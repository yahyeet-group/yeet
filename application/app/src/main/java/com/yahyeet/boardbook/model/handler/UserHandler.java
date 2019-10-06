package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserHandler {

    private IUserRepository userRepository;
    private List<UserHandlerListener> listeners = new ArrayList<>();

    public UserHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<User> create(User user) {
        return userRepository.create(user).thenApply((u) -> {
            notifyListenersOnUserAdd(u);

            return u;
        });
    }

    public CompletableFuture<User> find(String id) {
        return userRepository.find(id);
    }


    public CompletableFuture<User> update(User user) {
        return userRepository.update(user).thenApply((u) -> {
            notifyListenersOnUserUpdate(u);

            return u;
        });
    }


    public CompletableFuture<Void> remove(User user) {
        return userRepository.remove(user).thenApply((v) -> {
            notifyListenersOnUserRemove(user);

            return null;
        });
    }

    public CompletableFuture<List<User>> all() {
        return userRepository.all();
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
