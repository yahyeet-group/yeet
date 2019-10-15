package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class MatchHandler {

	private IMatchRepository matchRepository;
	private List<MatchHandlerListener> listeners = new ArrayList<>();

	public MatchHandler(IMatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	public CompletableFuture<Match> find(String id) {
		return matchRepository.find(id);
	}

	public CompletableFuture<Match> save(Match match) {
		if (match.getId() == null) {
			return matchRepository
				.create(match)
				.thenApplyAsync(createdMatch -> {
					notifyListenersOnMatchAdd(createdMatch);

					return createdMatch;
				});
		} else {
			return matchRepository.find(match.getId())
				.thenCompose(m -> matchRepository
					.update(match)
					.thenApplyAsync(updatedMatch -> {
						notifyListenersOnMatchUpdate(updatedMatch);

						return updatedMatch;
					}))
				.exceptionally(error -> {
					try {
						return matchRepository.create(match).thenApply(createdGame -> {
							notifyListenersOnMatchAdd(match);

							return createdGame;
						}).get();
					} catch (InterruptedException | ExecutionException e) {
						throw new CompletionException(e);
					}
				});
		}
	}

	public CompletableFuture<Void> remove(Match match) {
		return matchRepository.remove(match).thenApply((v) -> {
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
}
