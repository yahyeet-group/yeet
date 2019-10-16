package com.yahyeet.boardbook.activity.CreateingMatches;

import android.app.Activity;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CreateMatchDataHandler {
	//TODO See if this name should be kept

	private List<MatchPlayer> players = new ArrayList<>();
	private Game game;
	private Activity parentActivity;

	public CreateMatchDataHandler(Activity activity) {
		this.parentActivity = activity;
	}


	public void addPlayer(User user, GameTeam gameTeam, GameRole gameRole, Boolean win) {
		MatchPlayer mp = new MatchPlayer();
		mp.setUser(user);
		mp.setTeam(gameTeam);
		mp.setRole(gameRole);
		mp.setWin(win);

		players.add(mp);
	}

	public void addPlayer(User user, GameTeam gameTeam, Boolean win) {
		MatchPlayer mp = new MatchPlayer();
		mp.setUser(user);
		mp.setTeam(gameTeam);
		mp.setWin(win);

		players.add(mp);
	}

	public List<MatchPlayer> getPlayers(){
		return players;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Match finalizeMatch(){
		Match match = new Match();
		match.setPlayers(players);
		//TODO I suppose match should hold what game it is playing.
		return match;
	}


}

