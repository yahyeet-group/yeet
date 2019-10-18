package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.handler.populator.MatchPlayerPopulator;
import com.yahyeet.boardbook.model.handler.populator.MatchPopulator;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MatchHandler implements IRepositoryListener<Match> {

	private IMatchPlayerRepository matchPlayerRepository;
	private IMatchRepository matchRepository;
	private List<MatchHandlerListener> listeners = new ArrayList<>();

	private MatchPopulator matchPopulator;
	private MatchPlayerPopulator matchPlayerPopulator;

	public MatchHandler(IMatchRepository matchRepository,
											IMatchPlayerRepository matchPlayerRepository,
											IGameRepository gameRepository,
											IGameRoleRepository gameRoleRepository,
											IUserRepository userRepository) {
		this.matchPlayerRepository = matchPlayerRepository;
		this.matchRepository = matchRepository;

		matchPopulator = new MatchPopulator(gameRepository, matchPlayerRepository);
		matchPlayerPopulator = new MatchPlayerPopulator(gameRoleRepository, matchRepository, userRepository);
	}

	public CompletableFuture<Match> find(String id) {
		return matchRepository.find(id).thenCompose(this::populate);
	}

	public CompletableFuture<Match> save(Match match) {
		CompletableFuture<Match> savedMatchFuture = matchRepository.save(match);
		CompletableFuture<Void> savedMatchPlayersFuture = savedMatchFuture.thenCompose(savedMatch -> {
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

	public CompletableFuture<Match> populate(Match match) {
		AtomicReference<Match> fullyPopulatedMatch = new AtomicReference<>();

		return matchPopulator.populate(match).thenApply(populatedMatch -> {
			fullyPopulatedMatch.set(populatedMatch);

			return populatedMatch;
		}).thenCompose(populatedMatch -> {
			List<CompletableFuture<Void>> allFuturePopulatedMatchPlayers = populatedMatch
				.getMatchPlayers()
				.stream()
				.map(matchPlayer -> matchPlayerPopulator.populate(matchPlayer).<Void>thenCompose(populatedMatchPlayer -> {
					matchPlayer.setUser(populatedMatchPlayer.getUser());
					matchPlayer.setMatch(populatedMatchPlayer.getMatch());
					matchPlayer.setRole(populatedMatchPlayer.getRole());

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

	private void notifyListenersOnMatchRemove(Match match) {
		for (MatchHandlerListener listener : listeners) {
			listener.onRemoveMatch(match);
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
	public void onDelete(Match match) {
		notifyListenersOnMatchRemove(match);
	}
}
