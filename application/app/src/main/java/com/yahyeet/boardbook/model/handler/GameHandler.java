package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.handler.populator.GamePopulator;
import com.yahyeet.boardbook.model.handler.populator.GameTeamPopulator;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GameHandler implements IRepositoryListener<Game> {

	private IGameRepository gameRepository;
	private IGameRoleRepository gameRoleRepository;
	private IGameTeamRepository gameTeamRepository;
	private List<GameHandlerListener> listeners = new ArrayList<>();

	private GamePopulator gamePopulator;
	private GameTeamPopulator gameTeamPopulator;

	public GameHandler(IGameRepository gameRepository,
										 IGameRoleRepository gameRoleRepository,
										 IGameTeamRepository gameTeamRepository,
										 IMatchRepository matchRepository) {
		this.gameRepository = gameRepository;
		this.gameRoleRepository = gameRoleRepository;
		this.gameTeamRepository = gameTeamRepository;

		gamePopulator = new GamePopulator(matchRepository, gameTeamRepository);
		gameTeamPopulator = new GameTeamPopulator(gameRepository, gameRoleRepository);
	}

	public CompletableFuture<Game> find(String id) {
		return gameRepository.find(id).thenCompose(this::populate);
	}

	public CompletableFuture<Game> save(Game game) {
		checkGameValidity(game);

		CompletableFuture<Game> savedGameFuture = gameRepository.save(game);
		CompletableFuture<Void> savedGameTeamsAndRolesFuture = savedGameFuture.thenCompose(savedGame -> {
			game.setId(savedGame.getId());
			List<CompletableFuture<GameTeam>> savedGameTeamsFuture =
				game
					.getTeams()
					.stream()
					.map(team -> gameTeamRepository.save(team))
					.collect(Collectors.toList());

			CompletableFuture<Void> allOfSavedGameTeamsFuture = CompletableFuture.allOf(savedGameTeamsFuture.toArray(new CompletableFuture[0]));
			return allOfSavedGameTeamsFuture.thenApply(future -> savedGameTeamsFuture.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		}).thenCompose(savedGameTeams -> {
			for (int index = 0; index < game.getTeams().size(); ++index) {
				game.getTeams().get(index).setId(savedGameTeams.get(index).getId());
			}
			List<CompletableFuture<GameRole>> allFutureSavedGameRoles = new ArrayList<>();

			game.getTeams().forEach(team -> {
				team
					.getRoles()
					.forEach(role -> allFutureSavedGameRoles.add(gameRoleRepository.save(role)));
			});

			return CompletableFuture.allOf(allFutureSavedGameRoles.toArray(new CompletableFuture[0]));
		});

		return savedGameFuture
			.thenCombine(savedGameTeamsAndRolesFuture, (savedGame, nothing) -> savedGame)
			.thenCompose(this::populate);
	}

	public CompletableFuture<List<Game>> all() {
		return gameRepository.all().thenComposeAsync(games -> {
			List<CompletableFuture<Game>> completableFutures = games
				.stream()
				.map(this::populate)
				.collect(Collectors.toList());

			CompletableFuture<Void> allFutures = CompletableFuture.allOf(
				completableFutures
					.toArray(new CompletableFuture[0])
			);
			return allFutures.thenApplyAsync(
				future -> completableFutures
					.stream()
					.map(CompletableFuture::join)
					.collect(Collectors.toList()));
		});
	}

	public CompletableFuture<Game> populate(Game game) {
		AtomicReference<Game> fullyPopulatedGame = new AtomicReference<>();

		return gamePopulator.populate(game).thenApply(populatedGame -> {
			fullyPopulatedGame.set(populatedGame);

			return null;
		}).thenCompose(nothing -> {
			List<CompletableFuture<Void>> allFuturePopulatedGameTeams = fullyPopulatedGame
				.get()
				.getTeams()
				.stream()
				.map(team ->
					gameTeamPopulator
						.populate(team)
						.<Void>thenApply(populatedTeam -> {
							team.setGame(populatedTeam.getGame());
							team.getRoles().clear();
							populatedTeam.getRoles().forEach(team::addRole);

							return null;
						}))
				.collect(Collectors.toList());

			return CompletableFuture.allOf(allFuturePopulatedGameTeams.toArray(new CompletableFuture[0]));
		}).thenApply(nothing -> fullyPopulatedGame.get());
	}

	public void addListener(GameHandlerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(GameHandlerListener listener) {
		listeners.remove(listener);
	}

	private void notifyListenersOnGameAdd(Game game) {
		for (GameHandlerListener listener : listeners) {
			listener.onAddGame(game);
		}
	}

	private void notifyListenersOnGameUpdate(Game game) {
		for (GameHandlerListener listener : listeners) {
			listener.onUpdateGame(game);
		}
	}

	private void notifyListenersOnGameRemove(Game game) {
		for (GameHandlerListener listener : listeners) {
			listener.onRemoveGame(game);
		}
	}

	@Override
	public void onCreate(Game game) {
		notifyListenersOnGameAdd(game);
	}

	@Override
	public void onUpdate(Game game) {
		notifyListenersOnGameUpdate(game);
	}

	@Override
	public void onDelete(Game game) {
		notifyListenersOnGameRemove(game);
	}

	private void checkGameValidity(Game game) {
		if (game.getTeams() != null) {
			for (GameTeam team : game.getTeams()) {
				if (team.getGame() == null) {
					throw new IllegalArgumentException("Cannot create a team without a game");
				}
				if (team.getRoles() != null) {
					for (GameRole role : team.getRoles()) {
						if (role.getTeam() == null) {
							throw new IllegalArgumentException("Cannot create a role without a team");
						}
					}
				}
			}
		}
	}
}
