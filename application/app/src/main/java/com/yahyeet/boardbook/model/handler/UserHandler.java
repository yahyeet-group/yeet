package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A class that handles user entities
 */
public class UserHandler implements IRepositoryListener<User>, IEntityHandler<User> {
	private IUserRepository userRepository;
	private List<IUserHandlerListener> listeners = new ArrayList<>();

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

		this.userRepository.addListener(this);
	}

	@Override
	public CompletableFuture<User> find(String id) {
		return find(id, generatePopulatorConfig(false, false));
	}

	@Override
	public CompletableFuture<User> find(String id, Map<String, Boolean> config) {
		return userRepository.find(id).thenCompose(user -> populate(user, config));
	}

	@Override
	public CompletableFuture<User> save(User user) {
		return save(user, generatePopulatorConfig(false, false));
	}

	@Override
	public CompletableFuture<User> save(User user, Map<String, Boolean> config) {
		checkUserValidity(user);

		return userRepository.save(user).thenCompose(savedUser -> populate(savedUser, config));
	}

	@Override
	public CompletableFuture<List<User>> all() {
		return all(generatePopulatorConfig(false, false));
	}

	@Override
	public CompletableFuture<List<User>> all(Map<String, Boolean> config) {
		return userRepository.all().thenComposeAsync(users -> {
			List<CompletableFuture<User>> completableFutures = users
				.stream()
				.map(user -> populate(user, config))
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

	private CompletableFuture<User> populate(User user, Map<String, Boolean> config) {
		AtomicReference<User> fullyPopulatedUser = new AtomicReference<>();

		return userPopulator.populate(user, config).thenApply(populatedUser -> {
			fullyPopulatedUser.set(populatedUser);

			return null;
		}).thenCompose(nothing -> {
			List<CompletableFuture<Match>> allFuturePopulatedMatches =
				fullyPopulatedUser
					.get()
					.getMatches()
					.stream().map(match -> matchPopulator.populate(match, MatchHandler.generatePopulatorConfig(true, true)))
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
							matchPlayer.setTeam(populatedMatchPlayer.getTeam());
							matchPlayer.setRole(populatedMatchPlayer.getRole());

							return null;
						})));
				});

			return CompletableFuture.allOf(allFuturePopulatedMatchPlayers.toArray(new CompletableFuture[0]));
		}).thenApply(nothing -> fullyPopulatedUser.get());
	}

	public void addListener(IUserHandlerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IUserHandlerListener listener) {
		listeners.remove(listener);
	}

	private void notifyListenersOnUserAdd(User user) {
		for (IUserHandlerListener listener : listeners) {
			listener.onAddUser(user);
		}
	}

	private void notifyListenersOnUserUpdate(User user) {
		for (IUserHandlerListener listener : listeners) {
			listener.onUpdateUser(user);
		}
	}

	private void notifyListenersOnUserRemove(String id) {
		for (IUserHandlerListener listener : listeners) {
			listener.onRemoveUser(id);
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
	public void onDelete(String id) {
		notifyListenersOnUserRemove(id);
	}

	public static Map<String, Boolean> generatePopulatorConfig(
		boolean shouldPopulateFriends,
		boolean shouldPopulateMatches
	) {
		Map<String, Boolean> config = new HashMap<>();
		config.put("friends", shouldPopulateFriends);
		config.put("matches", shouldPopulateMatches);
		return config;
	}

	/**
	 * Checks whether a user contains valid data or not
	 *
	 * @param user User to be validated
	 */
	private void checkUserValidity(User user) {
		for (User friend : user.getFriends()) {
			if (friend.getId() == null) {
				throw new IllegalArgumentException("Cannot add friends that have no id");
			}
		}
	}
}
