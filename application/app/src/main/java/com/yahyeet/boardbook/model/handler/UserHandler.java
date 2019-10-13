package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class UserHandler {
	private IUserRepository userRepository;
	private IMatchRepository matchRepository;
	private List<UserHandlerListener> listeners = new ArrayList<>();

	public UserHandler(IUserRepository userRepository, IMatchRepository matchRepository) {
		this.userRepository = userRepository;
		this.matchRepository = matchRepository;
	}

	public CompletableFuture<User> find(String id) {
		return userRepository.find(id).thenCompose(this::populate);
	}

	public CompletableFuture<User> save(User user) {
		if (user.getId() == null) {
			return userRepository
				.create(user)
				.thenCompose(this::populate)
				.thenApplyAsync(createdUser -> {
					notifyListenersOnUserAdd(createdUser);

					return createdUser;
				});
		} else {
			return userRepository
				.update(user)
				.thenCompose(this::populate)
				.thenApplyAsync(updatedUser -> {
					notifyListenersOnUserUpdate(updatedUser);

					return updatedUser;
				});
		}
	}

	public CompletableFuture<User> saveWithId(User user) {
		return userRepository
			.create(user)
			.thenCompose(this::populate)
			.thenApplyAsync(createdUser -> {
				notifyListenersOnUserAdd(createdUser);

				return createdUser;
			});

	}

	public CompletableFuture<User> update(User user) {
		return userRepository.update(user).thenCompose((u) -> {
			notifyListenersOnUserUpdate(u);

			return this.populate(u);
		});
	}

	public CompletableFuture<Void> remove(User user) {
		return userRepository.remove(user).thenApply((v) -> {
			notifyListenersOnUserRemove(user);

			return null;
		});
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
		if (user.getId() == null) {
			throw new IllegalArgumentException("Cannot populate a user without an identifier");
		}

		return userRepository.findFriendsByUserId(user.getId()).thenApply(friends -> {
			user.setFriends(friends);

			return null;
		}).thenCompose(
			o -> matchRepository.findMatchesByUserId(user.getId())
		).thenApply(matches -> {
			user.setMatches(matches);

			return user;
		});
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
}
