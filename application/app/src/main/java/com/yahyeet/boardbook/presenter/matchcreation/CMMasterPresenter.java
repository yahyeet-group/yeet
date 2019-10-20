package com.yahyeet.boardbook.presenter.matchcreation;


import com.yahyeet.boardbook.activity.matchcreation.ICreateMatchActivity;

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

}
