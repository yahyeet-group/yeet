package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.Match;

/**
 * Interface that defines the methods a match handler listener must implement
 */
public interface MatchHandlerListener {
	void onAddMatch(Match match);

	void onUpdateMatch(Match match);

	void onRemoveMatch(String id);
}
