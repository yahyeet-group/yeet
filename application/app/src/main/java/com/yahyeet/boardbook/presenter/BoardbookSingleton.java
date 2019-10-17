package com.yahyeet.boardbook.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRoleRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameTeamRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchPlayer;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchPlayerRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseUserRepository;
import com.yahyeet.boardbook.model.firebase.service.FirebaseAuthService;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.service.IAuthService;

public class BoardbookSingleton {
	private static Boardbook instance;

	private BoardbookSingleton() {
	}

	static Boardbook getInstance() {
		if (instance == null) {
			FirebaseUserRepository userRepository = new FirebaseUserRepository(FirebaseFirestore.getInstance());
			FirebaseGameRepository gameRepository = new FirebaseGameRepository(FirebaseFirestore.getInstance());
			FirebaseMatchRepository matchRepository = new FirebaseMatchRepository(FirebaseFirestore.getInstance());
			FirebaseGameRoleRepository gameRoleRepository = new FirebaseGameRoleRepository(FirebaseFirestore.getInstance());
			FirebaseGameTeamRepository gameTeamRepository = new FirebaseGameTeamRepository(FirebaseFirestore.getInstance());
			FirebaseMatchPlayerRepository matchPlayerRepository = new FirebaseMatchPlayerRepository(FirebaseFirestore.getInstance());

			GameHandler gameHandler = new GameHandler(gameRepository, gameRoleRepository, gameTeamRepository, matchRepository);
			MatchHandler matchHandler = new MatchHandler(matchRepository, matchPlayerRepository, gameRepository, gameRoleRepository, userRepository);
			UserHandler userHandler = new UserHandler(userRepository, matchRepository, gameRoleRepository, gameRepository, matchPlayerRepository);

			IAuthService authService = new FirebaseAuthService(FirebaseAuth.getInstance(), userHandler);
			AuthHandler authHandler = new AuthHandler(authService);

			instance = new Boardbook(
				authHandler,
				userHandler,
				gameHandler,
				matchHandler
			);

			return instance;
		}

		return instance;
	}
}
