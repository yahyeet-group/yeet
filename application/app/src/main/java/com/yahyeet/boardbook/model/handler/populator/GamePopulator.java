package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GamePopulator {
	IMatchRepository matchRepository;
	IGameTeamRepository gameTeamRepository;

	public GamePopulator(IMatchRepository matchRepository, IGameTeamRepository gameTeamRepository) {
		this.matchRepository = matchRepository;
		this.gameTeamRepository = gameTeamRepository;
	}

	public CompletableFuture<Game> populate(Game game) {
		Game populatedGame = new Game(
			game.getName(),
			game.getDescription(),
			game.getDifficulty(),
			game.getMinPlayers(),
			game.getMaxPlayers()
		);
		game.setId(game.getId());

		CompletableFuture<List<Match>> futureMatches = matchRepository.findMatchesByGameId(game.getId());
		CompletableFuture<List<GameTeam>> futureTeams = gameTeamRepository.findTeamsByGameId(game.getId());

		return futureMatches.thenCombine(futureTeams, (matches, teams) -> {
			matches.forEach(populatedGame::addMatch);
			teams.forEach(populatedGame::addTeam);

			return populatedGame;
		});
	}
}
