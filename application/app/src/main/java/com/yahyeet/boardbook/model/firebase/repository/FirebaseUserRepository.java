package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseUserRepository extends AbstractFirebaseRepository<User> implements IUserRepository {

	public FirebaseUserRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public FirebaseEntity<User> fromModelEntityToFirebaseEntity(User entity) {
		return null;
	}

	@Override
	public FirebaseEntity<User> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return null;
	}

	@Override
	public String getCollectionName() {
		return "users";
	}

	@Override
	public CompletableFuture<List<User>> findFriendsByUserId(String id) {
		return CompletableFuture.completedFuture(new ArrayList<>());

	}
}
