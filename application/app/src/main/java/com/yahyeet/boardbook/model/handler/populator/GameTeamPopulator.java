package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GameTeamPopulator {
	IGameRepository gameRepository;
	IGameRoleRepository gameRoleRepository;

	public GameTeamPopulator(IGameRepository gameRepository, IGameRoleRepository gameRoleRepository) {
		this.gameRepository = gameRepository;
		this.gameRoleRepository = gameRoleRepository;
	}

	public CompletableFuture<GameTeam> populate(GameTeam gameTeam) {
		GameTeam populatedGameTeam = new GameTeam(gameTeam.getName());
		populatedGameTeam.setId(gameTeam.getId());

		CompletableFuture<Game> futureGame = gameRepository.find(gameTeam.getGame().getId());
		CompletableFuture<List<GameRole>> futureGameRoles = gameRoleRepository.findRolesByTeamId(populatedGameTeam.getId());

		return futureGame.thenCombine(futureGameRoles, (game, roles) -> {
			populatedGameTeam.setGame(game);
			roles.forEach(populatedGameTeam::addRole);

			return populatedGameTeam;
		});
	}
}
