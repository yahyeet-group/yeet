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

		List<Match> matches = new ArrayList<>();
		List<MatchPlayer> matchPlayers = new ArrayList<>();
		matchPlayers.add(new MatchPlayer("<3", user, new GameRole("he"), new GameTeam("sh"), false));
		matches.add(new Match("i", matchPlayers));
		matchPlayers = new ArrayList<>();
		matchPlayers.add(new MatchPlayer("<3", user, new GameRole("he"), new GameTeam("sh"), true));
		matches.add(new Match("i", matchPlayers));

		double d = stat.getWinrateFromMatches(matches, user);

		assertEquals(0.5, d);
	}

	@Test
	public void getWinrateFromMatchesIfNoMatches(){
		User user = new User();
		user.setId("hej");
		StatisticsUtil stat = new StatisticsUtil();

		List<Match> matches = new ArrayList<>();
		List<MatchPlayer> matchPlayers = new ArrayList<>();
		matches.add(new Match("i", matchPlayers));

		double d = stat.getWinrateFromMatches(matches, user);

		assertEquals(0.0, d);
	}
}
