package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.Map;

public abstract class FirebaseEntity<T extends AbstractEntity> {
	private String id;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public abstract Map<String, Object> toMap();

	public abstract T toModelType();

	public abstract void copyFields(FirebaseEntity<T> other);

	public static <T extends AbstractEntity> FirebaseEntity<T> fromModelType(T entity){
		throw new IllegalStateException("fromModelType has not been set up yet");
	}

	public static <T extends AbstractEntity> FirebaseEntity<T> fromDocument(DocumentSnapshot document){
		throw new IllegalStateException("fromDocument has not been set up yet");
	}

	public FirebaseEntity() {
	}

	public FirebaseEntity(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}