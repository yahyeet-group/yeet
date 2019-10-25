package com.yahyeet.boardbook.presenter.matchcreation;

/**
 * @Author Nox/Aaron Sandgren
 * This is the "CreateMatchMasterPresenter. This is served alongside the fragment presenters
 * to give them access to Activity methods and serve them with the MatchCreationDataObject.
 * This also creates and the final match and sends it to the MatchHandler to save
 *
 */

import com.yahyeet.boardbook.activity.matchcreation.ICreateMatchActivity;
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

	/**
	 * This is called to create and populate the match that is to be saved.
	 * It also gives this match to the MatchHandler to actually be saved.
	 */
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
