package com.yahyeet.boardbook.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseUserRepository;
import com.yahyeet.boardbook.model.firebase.service.FirebaseAuthService;

public class BoardbookSingleton {
	private static Boardbook instance;

	private BoardbookSingleton() {
	}

	static Boardbook getInstance() {
		if (instance == null) {

			FirebaseUserRepository userRepository = new FirebaseUserRepository(FirebaseFirestore.getInstance());
			FirebaseGameRepository gameRepository = new FirebaseGameRepository(FirebaseFirestore.getInstance());

			instance = new Boardbook(
				new FirebaseAuthService(FirebaseAuth.getInstance(), userRepository),
				userRepository,
				gameRepository,
				new FirebaseMatchRepository(FirebaseFirestore.getInstance(), gameRepository, userRepository)
			);

			return instance;
		}

		return instance;
	}
}
