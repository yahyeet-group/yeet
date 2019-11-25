package com.yahyeet.boardbook.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.firebase.repository.FirebaseGameRepository;
import com.yahyeet.boardbook.firebase.repository.FirebaseGameRoleRepository;
import com.yahyeet.boardbook.firebase.repository.FirebaseGameTeamRepository;
import com.yahyeet.boardbook.firebase.repository.FirebaseMatchPlayerRepository;
import com.yahyeet.boardbook.firebase.repository.FirebaseMatchRepository;
import com.yahyeet.boardbook.firebase.repository.FirebaseUserRepository;
import com.yahyeet.boardbook.firebase.service.FirebaseAuthService;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.GameHandler;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.service.IAuthService;

/**
 * Singleton that wraps the model
 */
public class BoardbookSingleton {
	private static Boardbook instance;
	private static final String FIREBASE_COLLECTION_PREFIX = "";

	private BoardbookSingleton() {
	}

	public static Boardbook getInstance() {
		if (instance == null) {
			FirebaseUserRepository userRepository = new FirebaseUserRepository(FirebaseFirestore.getInstance());
			FirebaseGameRepository gameRepository = new FirebaseGameRepository(FirebaseFirestore.getInstance());
			gameRepository.setCollectionNamePrefix(FIREBASE_COLLECTION_PREFIX);
			FirebaseMatchRepository matchRepository = new FirebaseMatchRepository(FirebaseFirestore.getInstance());
			matchRepository.setCollectionNamePrefix(FIREBASE_COLLECTION_PREFIX);
			FirebaseGameRoleRepository gameRoleRepository = new FirebaseGameRoleRepository(FirebaseFirestore.getInstance());
			gameRoleRepository.setCollectionNamePrefix(FIREBASE_COLLECTION_PREFIX);
			FirebaseGameTeamRepository gameTeamRepository = new FirebaseGameTeamRepository(FirebaseFirestore.getInstance());
			gameTeamRepository.setCollectionNamePrefix(FIREBASE_COLLECTION_PREFIX);
			FirebaseMatchPlayerRepository matchPlayerRepository = new FirebaseMatchPlayerRepository(FirebaseFirestore.getInstance());
			matchPlayerRepository.setCollectionNamePrefix(FIREBASE_COLLECTION_PREFIX);

			GameHandler gameHandler = new GameHandler(gameRepository, gameRoleRepository, gameTeamRepository, matchRepository);
			MatchHandler matchHandler = new MatchHandler(matchRepository, matchPlayerRepository, gameRepository, gameRoleRepository, gameTeamRepository, userRepository);
			UserHandler userHandler = new UserHandler(userRepository, matchRepository, gameRoleRepository, gameTeamRepository, gameRepository, matchPlayerRepository);

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
