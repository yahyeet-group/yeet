package com.yahyeet.boardbook.model.firebase.repository;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.Map;
import java.util.function.Function;

public abstract class AbstractFirebaseEntity<T extends AbstractEntity> {
	private String id;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public abstract Map<String, Object> toMap();

	public abstract T toModelType();

	public AbstractFirebaseEntity() {
	}

	public AbstractFirebaseEntity(String id) {
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