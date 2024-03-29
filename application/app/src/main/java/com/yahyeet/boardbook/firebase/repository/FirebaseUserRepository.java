package com.yahyeet.boardbook.firebase.repository;

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
	private Map<String, List<String>> friendIdsCache = new HashMap<>();

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
		List<String> cachedFriendIds = friendIdsCache.get(id);
		if (cachedFriendIds != null) {
			List<CompletableFuture<User>> futureFriends = cachedFriendIds.stream().map(this::find).collect(Collectors.toList());

			return CompletableFuture
				.allOf(futureFriends.toArray(new CompletableFuture[0]))
				.thenApply(future -> futureFriends
					.stream()
					.map(CompletableFuture::join)
					.collect(Collectors.toList())
				);
		}

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

			//friendIdsCache.put(id, friendIds);

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
				.map(this::find)
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
		CompletableFuture<List<User>> futureLocalAddedFriendIds = findFriendsByUserId(savedEntity.getId()).thenApply(remoteFriends -> {
				//friendIdsCache.put(entity.getId(), remoteFriends.stream().map(AbstractEntity::getId).collect(Collectors.toList()));

				return entity
					.getFriends()
					.stream()
					.filter(localFriend ->
						remoteFriends
							.stream()
							.noneMatch(remoteFriend -> remoteFriend.equals(localFriend))
					)
					.collect(Collectors.toList()
					);
			}
		);

		CompletableFuture<Void> futureSavedFriends = futureLocalAddedFriendIds
			.thenCompose(addedFriends -> {
				List<CompletableFuture<Void>> addedFriendsFuture = addedFriends
					.stream()
					.map(addedFriend -> addFriend(entity.getId(), addedFriend.getId()))
					.collect(Collectors.toList());

				return CompletableFuture.allOf(addedFriendsFuture.toArray(new CompletableFuture[0]));
			});

		return futureLocalAddedFriendIds
			.thenCombine(futureSavedFriends, (addedFriends, nothing) -> addedFriends)
			.thenApply(addedFriends -> {
				addedFriends.forEach(friend -> friendIdsCache.get(entity.getId()).add(friend.getId()));

				return null;
			});
	}

	/**
	 * Connects two user in a pivot collection in Firebase Firestore
	 *
	 * @param leftId  First user
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
		}).thenApply(nothing -> {
			List<String> leftUserCachedFriends = friendIdsCache.get(leftId);
			List<String> rightUserCachedFriends = friendIdsCache.get(rightId);

			if (leftUserCachedFriends != null) {
				leftUserCachedFriends.add(rightId);
			}

			if (rightUserCachedFriends != null) {
				rightUserCachedFriends.add(leftId);
			}

			return null;
		});
	}

	private String getFriendsCollectionName() {
		return getCollectionNamePrefix() + "_friends_user_to_user";
	}
}
