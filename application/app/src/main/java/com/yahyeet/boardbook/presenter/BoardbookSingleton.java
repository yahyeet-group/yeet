package com.yahyeet.boardbook.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRepository;
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
			FirebaseMatchRepository matchRepository = new FirebaseMatchRepository(FirebaseFirestore.getInstance(), gameRepository, userRepository);

			GameHandler gameHandler = new GameHandler(gameRepository);
			MatchHandler matchHandler = new MatchHandler(matchRepository);
			UserHandler userHandler = new UserHandler(userRepository, matchRepository);

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
