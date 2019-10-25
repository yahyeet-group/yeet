package com.yahyeet.boardbook.presenter.matchcreation;

import android.app.Activity;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Nox/Aaron Sandgren
 * THis is the dataobject where during the wizard the selected game, selected players
 * and the team configuration is saved to be able to when done to serve the data
 * to a Method that saves the match.
 * This object gets instantiated every time the Wizard is started anew(the user has
 * left it completely and gone back to the home activity). And the this is available
 * through the MasterPresenter and therefore the individual presenters.
 * All the methods are just about modifying the data.
 */
public class MatchCreationDataObject {

	private List<MatchPlayer> players = new ArrayList<>();
	private List<User> selectedPlayers = new ArrayList<>();

	private Game game;
	// private Activity parentActivity;

	public MatchCreationDataObject(Activity activity) {
		// TODO: Removed for now, but can get back if necessary later
		//this.parentActivity = activity;
	}

	public MatchCreationDataObject(){}

	public List<User> getSelectedPlayers() {
		return selectedPlayers;
	}

	public void addSelectedPlayer(User user){
		selectedPlayers.add(user);
	}

	public void removeSelectedPlayer(User user){
		selectedPlayers.remove(user);
	}

	public void clearSelectedPlayers(){
		selectedPlayers.clear();
	}

	public void addPlayer(User user, GameRole gameRole, GameTeam gameTeam, Boolean win) {
		MatchPlayer mp = new MatchPlayer(user, gameRole, gameTeam, win);
		players.add(mp);
	}

	public void addPlayer(User user, GameTeam gameTeam, Boolean win) {
		MatchPlayer mp = new MatchPlayer(user, null, gameTeam, win);
		players.add(mp);
	}

	public void addPlayer(User user , Boolean win){
		MatchPlayer mp = new MatchPlayer(user, null, null, win);
		players.add(mp);
	}

	public void addPlayer(MatchPlayer mp){
		players.add(mp);
	}

	public void addPlayer(List<MatchPlayer> mpl){
		players.addAll(mpl);
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

}

