package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserHandler {

    private IUserRepository userRepository;
    private List<UserHandlerListener> listeners = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public UserHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<User> create(User user) {
        return userRepository.create(user).thenApply((u) -> {
            addUser(u);

            notifyListenersOnUserAdd(u);

            return u;
        });
    }

    public CompletableFuture<User> find(String id) {
        User user = findUser(id);

        if (user == null) {
            return userRepository.find(id).thenApply((u -> {
                addUser(u);

                return u;
            }));
        }

        return CompletableFuture.completedFuture(user);
    }


    public CompletableFuture<User> update(User user) {
        return userRepository.update(user).thenApply((u) -> {
            updateUser(u);

            notifyListenersOnUserUpdate(u);

            return u;
        });
    }


    public CompletableFuture<Void> remove(User user) {
        return userRepository.remove(user).thenApply((v) -> {
            removeUser(user);

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

    private User findUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    private void addUser(User user) {
        users.add(user);
    }

    private void updateUser(User user) {
        for (int index = 0; index < users.size(); ++index) {
            if (user.getId().equals(users.get(index).getId())) {
                users.set(index, user);
            }
        }
    }

    private void removeUser(User user) {
        for (int index = 0; index < users.size(); ++index) {
            if (user.getId().equals(users.get(index).getId())) {
                users.remove(index);
                return;
            }
        }
    }
}
