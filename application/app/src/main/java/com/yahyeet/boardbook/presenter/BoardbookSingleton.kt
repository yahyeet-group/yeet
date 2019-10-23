package com.yahyeet.boardbook.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yahyeet.boardbook.model.Boardbook
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRepository
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameRoleRepository
import com.yahyeet.boardbook.model.firebase.repository.FirebaseGameTeamRepository
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchPlayerRepository
import com.yahyeet.boardbook.model.firebase.repository.FirebaseMatchRepository
import com.yahyeet.boardbook.model.firebase.repository.FirebaseUserRepository
import com.yahyeet.boardbook.model.firebase.service.FirebaseAuthService
import com.yahyeet.boardbook.model.handler.AuthHandler
import com.yahyeet.boardbook.model.handler.GameHandler
import com.yahyeet.boardbook.model.handler.MatchHandler
import com.yahyeet.boardbook.model.handler.UserHandler
import com.yahyeet.boardbook.model.service.IAuthService

object BoardbookSingleton {
    private var instance: Boardbook? = null
    private val FIREBASE_COLLECTION_PREFIX = ""

    fun getInstance(): Boardbook {
        if (instance == null) {
            val userRepository = FirebaseUserRepository(FirebaseFirestore.getInstance())
            userRepository.collectionNamePrefix = FIREBASE_COLLECTION_PREFIX
            val gameRepository = FirebaseGameRepository(FirebaseFirestore.getInstance())
            gameRepository.collectionNamePrefix = FIREBASE_COLLECTION_PREFIX
            val matchRepository = FirebaseMatchRepository(FirebaseFirestore.getInstance())
            matchRepository.collectionNamePrefix = FIREBASE_COLLECTION_PREFIX
            val gameRoleRepository = FirebaseGameRoleRepository(FirebaseFirestore.getInstance())
            gameRoleRepository.collectionNamePrefix = FIREBASE_COLLECTION_PREFIX
            val gameTeamRepository = FirebaseGameTeamRepository(FirebaseFirestore.getInstance())
            gameTeamRepository.collectionNamePrefix = FIREBASE_COLLECTION_PREFIX
            val matchPlayerRepository = FirebaseMatchPlayerRepository(FirebaseFirestore.getInstance())
            matchPlayerRepository.collectionNamePrefix = FIREBASE_COLLECTION_PREFIX

            val gameHandler = GameHandler(gameRepository, gameRoleRepository, gameTeamRepository, matchRepository)
            val matchHandler = MatchHandler(matchRepository, matchPlayerRepository, gameRepository, gameRoleRepository, gameTeamRepository, userRepository)
            val userHandler = UserHandler(userRepository, matchRepository, gameRoleRepository, gameTeamRepository, gameRepository, matchPlayerRepository)

            val authService = FirebaseAuthService(FirebaseAuth.getInstance(), userHandler)
            val authHandler = AuthHandler(authService)

            instance = Boardbook(
                    authHandler,
                    userHandler,
                    gameHandler,
                    matchHandler
            )

            return instance
        }

        return instance
    }
}
