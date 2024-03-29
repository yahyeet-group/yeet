package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IGameRoleRepository;
import com.yahyeet.boardbook.model.repository.IGameTeamRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.concurrent.CompletableFuture;

public class MatchPlayerPopulator {
	private IGameRoleRepository gameRoleRepository;
	private IGameTeamRepository gameTeamRepository;
	private IMatchRepository matchRepository;
	private IUserRepository userRepository;

	public MatchPlayerPopulator(IGameRoleRepository gameRoleRepository, IGameTeamRepository gameTeamRepository, IMatchRepository matchRepository, IUserRepository userRepository) {
		this.gameRoleRepository = gameRoleRepository;
		this.gameTeamRepository = gameTeamRepository;
		this.matchRepository = matchRepository;
		this.userRepository = userRepository;
	}

	public CompletableFuture<MatchPlayer> populate(MatchPlayer matchPlayer) {
		MatchPlayer populatedMatchPlayer = new MatchPlayer(null, null, null, matchPlayer.getWin());
		populatedMatchPlayer.setId(matchPlayer.getId());

		CompletableFuture<GameRole> futureGameRole;
		if (matchPlayer.getRole() == null) {
			futureGameRole = CompletableFuture.completedFuture(null);
		} else {
			futureGameRole = gameRoleRepository.find(matchPlayer.getRole().getId());
		}
		CompletableFuture<GameTeam> futureGameTeam;
		if (matchPlayer.getTeam() == null) {
			futureGameTeam = CompletableFuture.completedFuture(null);
		} else {
			futureGameTeam = gameTeamRepository.find(matchPlayer.getTeam().getId());
		}
		CompletableFuture<Match> futureMatch = matchRepository.find(matchPlayer.getMatch().getId());
		CompletableFuture<User> futureUser = userRepository.find(matchPlayer.getUser().getId());

		return futureGameRole.thenCombine(futureMatch, (gameRole, match) -> {
			populatedMatchPlayer.setRole(gameRole);
			populatedMatchPlayer.setMatch(match);

			return null;
		}).thenCombine(futureUser, (nothing, user) -> {
			populatedMatchPlayer.setUser(user);

			return null;
		}).thenCombine(futureGameTeam, (nothing, gameTeam) -> {
			populatedMatchPlayer.setTeam(gameTeam);

			return populatedMatchPlayer;
		});
	}
}
