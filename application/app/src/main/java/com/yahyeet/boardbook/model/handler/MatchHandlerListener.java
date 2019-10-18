package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;

public interface MatchHandlerListener {
	void onAddMatch(Match match);

	void onUpdateMatch(Match match);

	void onRemoveMatch(Match match);
}
