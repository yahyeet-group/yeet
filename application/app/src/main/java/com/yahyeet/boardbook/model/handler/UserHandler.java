package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.populator.MatchPlayerPopulator;
import com.yahyeet.boardbook.model.handler.populator.MatchPopulator;
import com.yahyeet.boardbook.model.handler.populator.UserPopulator;
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
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class UserHandler implements IRepositoryListener<User> {
	private IUserRepository userRepository;
	private List<UserHandlerListener> listeners = new ArrayList<>();

	private MatchPlayerPopulator matchPlayerPopulator;
	private MatchPopulator matchPopulator;
	private UserPopulator userPopulator;

	public UserHandler(IUserRepository userRepository,
										 IMatchRepository matchRepository,
										 IGameRoleRepository gameRoleRepository,
										 IGameTeamRepository gameTeamRepository,
										 IGameRepository gameRepository,
										 IMatchPlayerRepository matchPlayerRepository) {
		this.userRepository = userRepository;
		matchPlayerPopulator = new MatchPlayerPopulator(gameRoleRepository, gameTeamRepository, matchRepository, userRepository);
		matchPopulator = new MatchPopulator(gameRepository, matchPlayerRepository);
		userPopulator = new UserPopulator(matchPlayerRepository, matchRepository, userRepository);
	}

	public CompletableFuture<User> find(String id) {
		return userRepository.find(id).thenCompose(this::populate);
	}

	public CompletableFuture<User> save(User user) {
		return userRepository.save(user).thenCompose(this::populate);
	}

	public CompletableFuture<List<User>> all() {
		return userRepository.all().thenComposeAsync(users -> {
			List<CompletableFuture<User>> completableFutures = users
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

	public CompletableFuture<User> populate(User user) {
		AtomicReference<User> fullyPopulatedUser = new AtomicReference<>();

		return userPopulator.populate(user).thenApply(populatedUser -> {
			fullyPopulatedUser.set(populatedUser);

			return null;
		}).thenCompose(nothing -> {
			List<CompletableFuture<Match>> allFuturePopulatedMatches =
				fullyPopulatedUser
					.get()
					.getMatches()
					.stream().map(match -> matchPopulator.populate(match))
					.collect(Collectors.toList());

			CompletableFuture<Void> allOfFutureFuturePopulatedMatches = CompletableFuture.allOf(allFuturePopulatedMatches.toArray(new CompletableFuture[0]));

			return allOfFutureFuturePopulatedMatches.thenApply(future ->
				allFuturePopulatedMatches
					.stream()
					.map(CompletableFuture::join)
					.collect(Collectors.toList()));
		}).thenApply(populatedMatches -> {
			fullyPopulatedUser.get().getMatches().clear();
			populatedMatches.forEach(populatedMatch -> fullyPopulatedUser.get().addMatch(populatedMatch));

			return populatedMatches;
		}).thenCompose(populatedMatches -> {
			List<CompletableFuture<Void>> allFuturePopulatedMatchPlayers = new ArrayList<>();

			fullyPopulatedUser
				.get()
				.getMatches()
				.forEach(match -> {
					match
						.getMatchPlayers()
						.forEach(matchPlayer -> allFuturePopulatedMatchPlayers.add(matchPlayerPopulator.populate(matchPlayer).thenApply(populatedMatchPlayer -> {
							matchPlayer.setUser(populatedMatchPlayer.getUser());
							matchPlayer.setMatch(populatedMatchPlayer.getMatch());
							matchPlayer.setRole(populatedMatchPlayer.getRole());

							return null;
						})));
				});

			return CompletableFuture.allOf(allFuturePopulatedMatchPlayers.toArray(new CompletableFuture[0]));
		}).thenApply(nothing -> fullyPopulatedUser.get());
	}

	public void addListener(UserHandlerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(UserHandlerListener listener) {
		listeners.remove(listener);
	}

	private void notifyListenersOnUserAdd(User user) {
		for (UserHandlerListener listener : listeners) {
			listener.onAddUser(user);
		}
	}

	private void notifyListenersOnUserUpdate(User user) {
		for (UserHandlerListener listener : listeners) {
			listener.onUpdateUser(user);
		}
	}

	private void notifyListenersOnUserRemove(User user) {
		for (UserHandlerListener listener : listeners) {
			listener.onRemoveUser(user);
		}
	}

	@Override
	public void onCreate(User user) {
		notifyListenersOnUserAdd(user);
	}

	@Override
	public void onUpdate(User user) {
		notifyListenersOnUserUpdate(user);
	}

	@Override
	public void onDelete(User user) {
		notifyListenersOnUserRemove(user);
	}
}
