package com.yahyeet.boardbook.model.util;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;

import java.util.List;

public class StatisticsUtil {

	public double getWinrateFromMatches(List<Match> matches, User user){
		int numberOfMatches = matches.size();
		int numberOfWins = 0;

		for (Match match : matches) {
			if(match.getMatchPlayerByUser(user).isWin()){
				numberOfWins++;
			}
		}
		return (double) numberOfWins / numberOfMatches;
	}
}
