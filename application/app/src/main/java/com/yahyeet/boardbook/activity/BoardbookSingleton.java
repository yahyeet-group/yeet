package com.yahyeet.boardbook.activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.Boardbook;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseChatGroupRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseChatMessageRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchRepository;
import com.yahyeet.boardbook.model.firebase.repository.FirebaseUserRepository;
import com.yahyeet.boardbook.model.firebase.service.FirebaseAuthService;
import com.yahyeet.boardbook.model.handler.AuthHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.model.repository.IUserRepository;

public class BoardbookSingleton {
    private static Boardbook instance;

    private BoardbookSingleton() {
    }

    public static Boardbook getInstance() {
        if (instance == null) {

            IUserRepository userRepository = new FirebaseUserRepository(FirebaseFirestore.getInstance());

            UserHandler userHandler = new UserHandler(userRepository);

            instance = new Boardbook(
                    new FirebaseAuthService(FirebaseAuth.getInstance(), userHandler),
                    userHandler,
                    new FirebaseGameRepository(FirebaseFirestore.getInstance()),
                    new FirebaseMatchRepository(FirebaseFirestore.getInstance()),
                    new FirebaseChatGroupRepository(FirebaseFirestore.getInstance()),
                    new FirebaseChatMessageRepository(FirebaseFirestore.getInstance())
            );

            return instance;
        }

        return instance;
    }
}
