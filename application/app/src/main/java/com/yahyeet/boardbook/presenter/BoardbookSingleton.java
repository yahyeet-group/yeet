package com.yahyeet.boardbook.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.repository.FirebaseChatGroupRepository;
import com.yahyeet.boardbook.model.repository.FirebaseChatMessageRepository;
import com.yahyeet.boardbook.model.repository.FirebaseGameRepository;
import com.yahyeet.boardbook.model.repository.FirebaseMatchRepository;
import com.yahyeet.boardbook.model.repository.FirebaseUserRepository;
import com.yahyeet.boardbook.model.service.FirebaseAuthService;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.repository.IUserRepository;

public class BoardbookSingleton {
    private static Boardbook instance;

    private BoardbookSingleton() {
    }

    static Boardbook getInstance() {
        if (instance == null) {

            IUserRepository userRepository = new FirebaseUserRepository(FirebaseFirestore.getInstance());

            instance = new Boardbook(
                    new FirebaseAuthService(FirebaseAuth.getInstance(), userRepository),
                    userRepository,
                    new FirebaseGameRepository(FirebaseFirestore.getInstance()),
                    new FirebaseMatchRepository(FirebaseFirestore.getInstance())
            );

            return instance;
        }

        return instance;
    }
}
