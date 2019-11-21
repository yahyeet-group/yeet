package com.yahyeet.boardbook.firebase.repository;

import com.google.firebase.Timestamp;
import com.yahyeet.boardbook.model.entity.AbstractEntity;

import java.util.Map;

/**
 * Abstract generic class that defines methods and implements common functionality that Firebase
 * entities need
 *
 * @param <T>
 */
public abstract class AbstractFirebaseEntity<T extends AbstractEntity> {
	private String id;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	/**
	 * Creates a map of the class fields in was a way such that Firebase can store them
	 *
	 * @return A map
	 */
	public abstract Map<String, Object> toMap();

	/**
	 * Converts the Firebase entity to a model entity
	 *
	 * @return A model entity
	 */
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