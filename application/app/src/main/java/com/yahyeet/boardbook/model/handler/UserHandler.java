package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IGameRepository;
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
        CompletableFuture<User> userCompletableFuture;
        if (user.getId() == null) {
            userCompletableFuture = userRepository.create(user).thenApplyAsync(createdUser -> {
                notifyListenersOnUserAdd(createdUser);

                return createdUser;
            });
        } else {
            userCompletableFuture = userRepository.update(user).thenApplyAsync(updatedUser -> {
                notifyListenersOnUserUpdate(updatedUser);

                return updatedUser;
            });
        }

        if (user.getFriends() != null) {
            CompletableFuture<Void> userFriendsCompletableFuture = userCompletableFuture.thenComposeAsync(this::updateFriendsList);

            return userCompletableFuture.thenCombineAsync(userFriendsCompletableFuture, (savedUser, nothing) -> savedUser);
        }

        return userCompletableFuture;
    }

    public CompletableFuture<User> update(User user) {
        // TODO: update relation entities
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
        return userRepository.all().thenCompose(users -> {
            List<CompletableFuture<User>> completableFutures = users.stream().map(this::populate).collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
            return allFutures.thenApply(future -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        });
    }

    public CompletableFuture<User> populate(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Cannot populate a user without an identifier");
        }

        return this.find(user.getId()).thenApply(u -> {
            user.setName(u.getName());

            return u;
        }).thenCompose(u -> this.userRepository.findFriendsByUserId(user.getId())).thenApply(friends -> {
            user.setFriends(friends);

            return null;
        }).thenCompose(o -> this.matchRepository.findMatchesByUserId(user.getId())).thenApply(matches -> {
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

    private CompletableFuture<Void> updateFriendsList(User user) {
        return userRepository.findFriendsByUserId(user.getId())
                .thenComposeAsync(friends -> {
                    List<User> addedFriends = extractAddedFriends(user.getFriends(), friends);
                    List<User> removedFriends = extractRemovedFriends(user.getFriends(), friends);

                    List<CompletableFuture<Void>> completableFutures = addedFriends
                            .stream()
                            .map(addFriend -> userRepository.addFriend(user, addFriend))
                            .collect(Collectors.toList());
                    completableFutures.addAll(
                            removedFriends
                                    .stream()
                                    .map(removeFriend -> userRepository.removeFriend(user, removeFriend))
                                    .collect(Collectors.toList())
                    );
                    return CompletableFuture.allOf(
                            completableFutures.toArray(new CompletableFuture[0])
                    );
                });
    }

    private List<User> extractAddedFriends(List<User> localFriends, List<User> remoteFriends) {
        return localFriends
                .stream()
                .filter(localFriend ->
                        remoteFriends
                                .stream()
                                .noneMatch(remoteFriend ->
                                        remoteFriend
                                                .getId()
                                                .equals(localFriend.getId())))
                .collect(Collectors.toList());
    }

    private List<User> extractRemovedFriends(List<User> localFriends, List<User> remoteFriends) {
        return remoteFriends
                .stream()
                .filter(remoteFriend ->
                        localFriends
                                .stream()
                                .noneMatch(localFriend ->
                                        localFriend
                                                .getId()
                                                .equals(remoteFriend.getId())))
                .collect(Collectors.toList());
    }
}
