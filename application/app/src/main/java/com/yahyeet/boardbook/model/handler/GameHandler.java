package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.repository.IGameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GameHandler {

    private IGameRepository gameRepository;
    private List<GameHandlerListener> listeners = new ArrayList<>();
    private List<Game> games = new ArrayList<>();

    public GameHandler(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    CompletableFuture<Game> create(Game game) {
        return gameRepository.create(game).thenApply((u) -> {
            addGame(u);

            notifyListenersOnGameAdd(u);

            return u;
        });
    }

    CompletableFuture<Game> find(String id) {
        Game game = findGame(id);

        if (game == null) {
            return gameRepository.find(id);
        }

        return CompletableFuture.completedFuture(game);
    }


    CompletableFuture<Game> update(Game game) {
        return gameRepository.update(game).thenApply((u) -> {
            updateGame(u);

            notifyListenersOnGameUpdate(u);

            return u;
        });
    }


    CompletableFuture<Void> remove(Game game) {
        return gameRepository.remove(game).thenApply((v) -> {
            removeGame(game);

            notifyListenersOnGameRemove(game);

            return null;
        });
    }

    CompletableFuture<List<Game>> all() {
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

    private Game findGame(String id) {
        for (Game game : games) {
            if (game.getId().equals(id)) {
                return game;
            }
        }

        return null;
    }

    private void addGame(Game game) {
        games.add(game);
    }

    private void updateGame(Game game) {
        for (int index = 0; index < games.size(); ++index) {
            if (game.getId().equals(games.get(index).getId())) {
                games.set(index, game);
            }
        }
    }

    private void removeGame(Game game) {
        for (int index = 0; index < games.size(); ++index) {
            if (game.getId().equals(games.get(index).getId())) {
                games.remove(index);
                return;
            }
        }
    }
}
