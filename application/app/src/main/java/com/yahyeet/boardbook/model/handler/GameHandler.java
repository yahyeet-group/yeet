package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GameHandler {

    private IGameRepository gameRepository;
    private List<GameHandlerListener> listeners = new ArrayList<>();

    public GameHandler(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public CompletableFuture<Game> find(String id) {
        return gameRepository.find(id);
    }

    public CompletableFuture<Game> save(Game game) {
        if (game.getId() == null) {
            return gameRepository
              .create(game)
              .thenApplyAsync(createdGame -> {
                  notifyListenersOnGameAdd(createdGame);

                  return createdGame;
              });
        } else {
            return gameRepository
              .update(game)
              .thenApplyAsync(updatedGame -> {
                  notifyListenersOnGameUpdate(updatedGame);

                  return updatedGame;
              });
        }
    }

    public CompletableFuture<Void> remove(Game game) {
        return gameRepository.remove(game).thenApply((v) -> {
            notifyListenersOnGameRemove(game);

            return null;
        });
    }

    public CompletableFuture<List<Game>> all() {
        return gameRepository.all();
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
}
