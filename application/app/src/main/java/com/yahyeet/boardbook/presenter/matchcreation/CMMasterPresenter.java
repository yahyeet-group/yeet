package com.yahyeet.boardbook.presenter.matchcreation;


import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;

public class CMMasterPresenter {

	private MatchCreationDataObject cmdh;
	private ICreateMatchActivity activity;

	public CMMasterPresenter(ICreateMatchActivity activity) {
		this.activity = activity;
		cmdh = new MatchCreationDataObject();
	}

	public void goToConfigureTeams() {
		activity.goToConfigureTeams();
	}

	public void goToSelectGame(){
		activity.goToSelectGame();
	}

	public void goToSelectPlayers(){
		activity.goToSelectPlayers();
	}

	public MatchCreationDataObject getCmdh(){
		return cmdh;
	}

	public void finalizeMatch(){
		Match finalMatch = new Match(cmdh.getGame());
		for (MatchPlayer mp : cmdh.getPlayers()){
			finalMatch.addMatchPlayer(mp);
		}

		//TODO here do big save to FireBase Desu
	}
}
