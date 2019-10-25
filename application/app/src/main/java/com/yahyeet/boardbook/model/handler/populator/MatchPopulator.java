package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MatchPopulator {
	private IGameRepository gameRepository;
	private IMatchPlayerRepository matchPlayerRepository;

	public MatchPopulator(IGameRepository gameRepository, IMatchPlayerRepository matchPlayerRepository) {
		this.gameRepository = gameRepository;
		this.matchPlayerRepository = matchPlayerRepository;
	}

	public CompletableFuture<Match> populate(Match match, Map<String, Boolean> config) {
		Match populatedMatch = new Match(match.getId());

		Boolean shouldFetchGame = config.getOrDefault("game", false);
		Boolean shouldFetchMatchPlayers = config.getOrDefault("players", false);

		CompletableFuture<Game> futureGame = CompletableFuture.completedFuture(null);
		if (shouldFetchGame != null && shouldFetchGame) {
			futureGame = gameRepository.find(match.getGame().getId());
		}

		CompletableFuture<List<MatchPlayer>> futureMatchPlayers = CompletableFuture.completedFuture(new ArrayList<>());
		if (shouldFetchMatchPlayers != null && shouldFetchMatchPlayers) {
			futureMatchPlayers = matchPlayerRepository.findMatchPlayersByMatchId(populatedMatch.getId());
		}

		return futureGame.thenCombine(futureMatchPlayers, (game, matchPlayers) -> {
			populatedMatch.setGame(game);
			matchPlayers.forEach(populatedMatch::addMatchPlayer);

			return populatedMatch;
		});
	}
}
