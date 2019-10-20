package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MatchPopulator {
	private IGameRepository gameRepository;
	private IMatchPlayerRepository matchPlayerRepository;

	public MatchPopulator(IGameRepository gameRepository, IMatchPlayerRepository matchPlayerRepository) {
		this.gameRepository = gameRepository;
		this.matchPlayerRepository = matchPlayerRepository;
	}

	public CompletableFuture<Match> populate(Match match) {
		if(match.getGame() != null){
			Match populatedMatch = new Match(match.getId());

			CompletableFuture<Game> futureGame = gameRepository.find(match.getGame().getId());
			CompletableFuture<List<MatchPlayer>> futureMatchPlayers = matchPlayerRepository.findMatchPlayersByMatchId(populatedMatch.getId());

			return futureGame.thenCombine(futureMatchPlayers, (game, matchPlayers) -> {
				populatedMatch.setGame(game);
				matchPlayers.forEach(populatedMatch::addMatchPlayer);

				return populatedMatch;
			});
		}

		throw new IllegalStateException("Match cannot be populated without an attached game");
	}
}
