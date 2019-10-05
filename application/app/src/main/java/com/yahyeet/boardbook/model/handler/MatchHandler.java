package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MatchHandler {

    private IRepository<Match> matchRepository;
    private List<MatchHandlerListener> listeners = new ArrayList<>();
    private List<Match> matchs = new ArrayList<>();

    public MatchHandler(IRepository<Match> matchRepository) {
        this.matchRepository = matchRepository;
    }

    public CompletableFuture<Match> create(Match match) {
        return matchRepository.create(match).thenApply((u) -> {
            addMatch(u);

            notifyListenersOnMatchAdd(u);

            return u;
        });
    }

    public CompletableFuture<Match> find(String id) {
        Match match = findMatch(id);

        if (match == null) {
            return matchRepository.find(id).thenApply((m -> {
                addMatch(m);

                return m;
            }));
        }

        return CompletableFuture.completedFuture(match);
    }


    public CompletableFuture<Match> update(Match match) {
        return matchRepository.update(match).thenApply((u) -> {
            updateMatch(u);

            notifyListenersOnMatchUpdate(u);

            return u;
        });
    }


    public CompletableFuture<Void> remove(Match match) {
        return matchRepository.remove(match).thenApply((v) -> {
            removeMatch(match);

            notifyListenersOnMatchRemove(match);

            return null;
        });
    }

    public CompletableFuture<List<Match>> all() {
        return matchRepository.all();
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

    private Match findMatch(String id) {
        for (Match match : matchs) {
            if (match.getId().equals(id)) {
                return match;
            }
        }

        return null;
    }

    private void addMatch(Match match) {
        matchs.add(match);
    }

    private void updateMatch(Match match) {
        for (int index = 0; index < matchs.size(); ++index) {
            if (match.getId().equals(matchs.get(index).getId())) {
                matchs.set(index, match);
            }
        }
    }

    private void removeMatch(Match match) {
        for (int index = 0; index < matchs.size(); ++index) {
            if (match.getId().equals(matchs.get(index).getId())) {
                matchs.remove(index);
                return;
            }
        }
    }
}
