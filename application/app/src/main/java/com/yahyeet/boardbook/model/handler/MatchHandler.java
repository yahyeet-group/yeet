package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.handler.populator.MatchPlayerPopulator;
import com.yahyeet.boardbook.model.handler.populator.MatchPopulator;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A class that handles match entities
 */
public class MatchHandler implements IRepositoryListener<Match>, EntityHandler<Match> {

	private IMatchPlayerRepository matchPlayerRepository;
	private IMatchRepository matchRepository;
	private List<MatchHandlerListener> listeners = new ArrayList<>();

	private MatchPopulator matchPopulator;
	private MatchPlayerPopulator matchPlayerPopulator;

	public MatchHandler(IMatchRepository matchRepository,
											IMatchPlayerRepository matchPlayerRepository,
											IGameRepository gameRepository,
											IGameRoleRepository gameRoleRepository,
											IGameTeamRepository gameTeamRepository,
											IUserRepository userRepository) {
		this.matchPlayerRepository = matchPlayerRepository;
		this.matchRepository = matchRepository;

		matchPopulator = new MatchPopulator(gameRepository, matchPlayerRepository);
		matchPlayerPopulator = new MatchPlayerPopulator(gameRoleRepository, gameTeamRepository, matchRepository, userRepository);

		this.matchRepository.addListener(this);
	}

	@Override
	public CompletableFuture<Match> find(String id) {
		return matchRepository.find(id).thenCompose(this::populate);
	}

	@Override
	public CompletableFuture<Match> save(Match match) {
		checkMatchValidity(match);

		CompletableFuture<Match> savedMatchFuture = matchRepository.save(match);
		CompletableFuture<Void> savedMatchPlayersFuture = savedMatchFuture.thenCompose(savedMatch -> {
			match.setId(savedMatch.getId());
			List<CompletableFuture<MatchPlayer>> savedMatchPlayerFutures =
				match
					.getMatchPlayers()
					.stream()
					.map(matchPlayer -> matchPlayerRepository.save(matchPlayer))
					.collect(Collectors.toList());

			return CompletableFuture.allOf(savedMatchPlayerFutures.toArray(new CompletableFuture[0]));
		});

		return savedMatchFuture
			.thenCombine(savedMatchPlayersFuture, (savedMatch, nothing) -> savedMatch)
			.thenCompose(this::populate);
	}

	@Override
	public CompletableFuture<List<Match>> all() {
		return matchRepository.all().thenComposeAsync(matches -> {
			List<CompletableFuture<Match>> completableFutures = matches
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

	private CompletableFuture<Match> populate(Match match) {
		AtomicReference<Match> fullyPopulatedMatch = new AtomicReference<>();

		return matchPopulator.populate(match).thenApply(populatedMatch -> {
			fullyPopulatedMatch.set(populatedMatch);

			return populatedMatch;
		}).thenCompose(populatedMatch -> {
			List<CompletableFuture<Void>> allFuturePopulatedMatchPlayers = populatedMatch
				.getMatchPlayers()
				.stream()
				.map(matchPlayer -> matchPlayerPopulator.populate(matchPlayer).<Void>thenApply(populatedMatchPlayer -> {
					matchPlayer.setUser(populatedMatchPlayer.getUser());
					matchPlayer.setMatch(populatedMatchPlayer.getMatch());
					matchPlayer.setRole(populatedMatchPlayer.getRole());
					matchPlayer.setTeam(populatedMatchPlayer.getTeam());

					return null;
				})).collect(Collectors.toList());

			return CompletableFuture.allOf(allFuturePopulatedMatchPlayers.toArray(new CompletableFuture[0]));
		}).thenApply(nothing -> fullyPopulatedMatch.get());
	}

	public void addListener(MatchHandlerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(MatchHandlerListener listener) {
		listeners.remove(listener);
	}

	private void notifyListenersOnMatchAdd(Match match) {
		for (MatchHandlerListener listener : listeners) {
			listener.onAddMatch(match);
		}
	}

	private void notifyListenersOnMatchUpdate(Match match) {
		for (MatchHandlerListener listener : listeners) {
			listener.onUpdateMatch(match);
		}
	}

	private void notifyListenersOnMatchRemove(String id) {
		for (MatchHandlerListener listener : listeners) {
			listener.onRemoveMatch(id);
		}
	}

	@Override
	public void onCreate(Match match) {
		notifyListenersOnMatchAdd(match);
	}

	@Override
	public void onUpdate(Match match) {
		notifyListenersOnMatchUpdate(match);
	}

	@Override
	public void onDelete(String id) {
		notifyListenersOnMatchRemove(id);
	}

	/**
	 * Checks whether a match contains valid data or not
	 *
	 * @param match Match to be validated
	 */
	private void checkMatchValidity(Match match) {
		if (match.getGame() == null) {
			throw new IllegalArgumentException("Cannot create a match without a game");
		}

		if (match.getGame().getId() == null) {
			throw new IllegalArgumentException("Cannot create a match with a game that has no id");
		}

		if (match.getMatchPlayers().isEmpty()) {
			throw new IllegalArgumentException("Cannot create a match without any players");
		}

		for (MatchPlayer player : match.getMatchPlayers()) {
			if (player.getMatch() == null) {
				throw new IllegalArgumentException("Cannot create a match where a match player has no match");
			}

			if (player.getUser() == null) {
				throw new IllegalArgumentException("Cannot create a match where a match player has no user");
			}

			if (player.getUser().getId() == null) {
				throw new IllegalArgumentException("Cannot create a match where a match player with a user that has no id");
			}

			if (player.getTeam() == null && player.getRole() != null) {
				throw new IllegalArgumentException("Cannot create a match where a match player with a role but no team");
			}

			if (player.getTeam() != null && player.getTeam().getId() == null) {
				throw new IllegalArgumentException("Cannot create a match where a match player with a team that has no id");
			}

			if (player.getRole() != null && player.getRole().getId() == null) {
				throw new IllegalArgumentException("Cannot create a match where a match player with a role that has no id");
			}
		}
	}
}