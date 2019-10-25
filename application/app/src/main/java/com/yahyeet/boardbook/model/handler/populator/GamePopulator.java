package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GamePopulator {
	private IMatchRepository matchRepository;
	private IGameTeamRepository gameTeamRepository;

	public GamePopulator(IMatchRepository matchRepository, IGameTeamRepository gameTeamRepository) {
		this.matchRepository = matchRepository;
		this.gameTeamRepository = gameTeamRepository;
	}

	public CompletableFuture<Game> populate(Game game, Map<String, Boolean> config) {
		Game populatedGame = new Game(
			game.getName(),
			game.getDescription(),
			game.getDifficulty(),
			game.getMinPlayers(),
			game.getMaxPlayers()
		);
		populatedGame.setId(game.getId());

		Boolean shouldFetchMatches = config.getOrDefault("matches", true);
		Boolean shouldFetchTeams = config.getOrDefault("teams", true);

		CompletableFuture<List<Match>> futureMatches = CompletableFuture.completedFuture(new ArrayList<>());
		if (shouldFetchMatches == null || shouldFetchMatches) {
			futureMatches = matchRepository.findMatchesByGameId(game.getId());
		}

		CompletableFuture<List<GameTeam>> futureTeams = CompletableFuture.completedFuture(new ArrayList<>());
		if (shouldFetchTeams == null || shouldFetchTeams) {
			futureTeams = gameTeamRepository.findTeamsByGameId(game.getId());
		}

		return futureMatches.thenCombine(futureTeams, (matches, teams) -> {
			matches.forEach(populatedGame::addMatch);
			teams.forEach(populatedGame::addTeam);

			return populatedGame;
		});
	}
}
