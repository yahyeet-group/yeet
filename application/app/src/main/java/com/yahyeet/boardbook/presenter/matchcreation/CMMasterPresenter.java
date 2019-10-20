package com.yahyeet.boardbook.presenter.matchcreation;


import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.ICreateMatchActivity;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;

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
		System.out.println();
		BoardbookSingleton.getInstance().getMatchHandler()
			.save(finalMatch)
			.thenAccept(u -> activity.finalizeMatchCreation())
			.exceptionally(e -> {

				// TODO: Handle firebase not able to save match, dunno what to do exactly
				e.printStackTrace();
				return null;
		});
	}
}
