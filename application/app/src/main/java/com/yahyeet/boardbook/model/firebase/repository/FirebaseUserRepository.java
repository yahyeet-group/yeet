package com.yahyeet.boardbook.model.firebase.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * Firebase Firestore implementation of a user repository
 */
public class FirebaseUserRepository extends AbstractFirebaseRepository<User> implements IUserRepository {

	public FirebaseUserRepository(FirebaseFirestore firestore) {
		super(firestore);
	}

	@Override
	public AbstractFirebaseEntity<User> fromModelEntityToFirebaseEntity(User entity) {
		return FirebaseUser.fromModelType(entity);
	}

	@Override
	public AbstractFirebaseEntity<User> fromDocumentToFirebaseEntity(DocumentSnapshot document) {
		return FirebaseUser.fromDocument(document);
	}

	@Override
	public String getCollectionName() {
		return "users";
	}

	@Override
	public CompletableFuture<List<User>> findFriendsByUserId(String id) {
		CompletableFuture<List<User>> friendsPartOne = CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getFriendsCollectionName())
					.whereEqualTo("a", id)
					.get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(document -> document.getString("b"))
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(friendIds -> {
			List<CompletableFuture<User>> friendsFuture = friendIds
				.stream()
				.map(this::find)
				.collect(Collectors.toList());

			CompletableFuture<Void> allOfFriendsFuture = CompletableFuture.allOf(friendsFuture.toArray(new CompletableFuture[0]));

			return allOfFriendsFuture.thenApply(future -> friendsFuture.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		});

		CompletableFuture<List<User>> friendsPartTwo = CompletableFuture.supplyAsync(() -> {
			Task<QuerySnapshot> task =
				getFirestore()
					.collection(getFriendsCollectionName())
					.whereEqualTo("b", id)
					.get();

			try {
				QuerySnapshot querySnapshot = Tasks.await(task);

				return querySnapshot
					.getDocuments()
					.stream()
					.map(document -> document.getString("a"))
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		}).thenCompose(friendIds -> {
			List<CompletableFuture<User>> friendsFuture = friendIds
				.stream()
				.map(friendId -> find(friendId))
				.collect(Collectors.toList());

			CompletableFuture<Void> allOfFriendsFuture = CompletableFuture.allOf(friendsFuture.toArray(new CompletableFuture[0]));

			return allOfFriendsFuture.thenApply(future -> friendsFuture.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		});

		return friendsPartOne
			.thenCombine(friendsPartTwo, (partOne, partTwo) -> {
				partOne.addAll(partTwo);
				return partOne;
			});
	}

	@Override
	public CompletableFuture<Void> afterSave(User entity, AbstractFirebaseEntity<User> savedEntity) {
		return findFriendsByUserId(savedEntity.getId()).thenApply(remoteFriends ->
			entity
				.getFriends()
				.stream()
				.filter(localFriend ->
					remoteFriends
						.stream()
						.noneMatch(remoteFriend -> remoteFriend.equals(localFriend))
				)
				.collect(Collectors.toList()
				))
			.thenCompose(addedFriends -> {
				List<CompletableFuture<Void>> addedFriendsFuture = addedFriends
					.stream()
					.map(addedFriend -> addFriend(entity.getId(), addedFriend.getId()))
					.collect(Collectors.toList());

				return CompletableFuture.allOf(addedFriendsFuture.toArray(new CompletableFuture[0]));
			});
	}

	/**
	 * Connects two user in a pivot collection in Firebase Firestore
	 *
	 * @param leftId First user
	 * @param rightId Second user
	 * @return A completable future that when resolves denotes that the operation is finished, if the
	 * operation is unsuccessful it throws an error
	 */
	private CompletableFuture<Void> addFriend(String leftId, String rightId) {
		return CompletableFuture.supplyAsync(() -> {
			Map<String, Object> data = new HashMap<>();
			data.put("a", leftId);
			data.put("b", rightId);
			Task<DocumentReference> task = getFirestore().collection(getFriendsCollectionName()).add(data);

			try {
				Tasks.await(task);

				return null;
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private String getFriendsCollectionName() {
		return getCollectionNamePrefix() + "_friends_user_to_user";
	}
}
