package com.yahyeet.boardbook.model.util;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;

import java.util.List;

public class StatisticsUtil {

	public double getWinrateFromMatches(List<Match> matches, User user){
		int numberOfMatches = matches.size();
		int numberOfWins = 0;

		for (Match match : matches) {
			MatchPlayer mp = match.getMatchPlayerByUser(user);
			if(mp != null && mp.isWin()){
				numberOfWins++;
			}
		}
		if(numberOfMatches == 0){
			return 0.0;
		}
		return (double) numberOfWins / numberOfMatches;
	}
}
