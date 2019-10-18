package com.yahyeet.boardbook;

import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.util.StatisticsUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static junit.framework.TestCase.assertEquals;

public class StatisticsUtilTest {

	@Test
	public void getWinrateFromMatches(){
		User user = new User();
		user.setId("hej");
		StatisticsUtil stat = new StatisticsUtil();

		Match lostMatch = new Match();
		lostMatch.addMatchPlayer(new MatchPlayer(user, new GameRole("he"), null, false));
		user.addMatch(lostMatch);

		Match wonMatch = new Match();
		wonMatch.addMatchPlayer(new MatchPlayer(user, new GameRole("he"), null, true));
		user.addMatch(wonMatch);

		double d = stat.getWinrateFromMatches(user.getMatches(), user);

		assertEquals(0.5, d);
	}

	@Test
	public void getWinrateFromMatchesIfNoMatches(){
		User user = new User();
		user.setId("hej");
		StatisticsUtil stat = new StatisticsUtil();

		user.addMatch(new Match("i"));

		double d = stat.getWinrateFromMatches(user.getMatches(), user);

		assertEquals(0.0, d);
	}
}
