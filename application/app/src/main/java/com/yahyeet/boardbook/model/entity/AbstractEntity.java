package com.yahyeet.boardbook.model.entity;

import androidx.annotation.Nullable;

public abstract class AbstractEntity {
	private String id;

	public AbstractEntity(String id) {
		this.id = id;
	}

	public AbstractEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof AbstractEntity)) {
			return false;
		}

		AbstractEntity entity = (AbstractEntity) obj;
		return id.equals(entity.id);
	}
}
