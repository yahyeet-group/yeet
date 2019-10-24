package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;

/**
 * Interface that defines the methods a user handler listener must implement
 */
public interface UserHandlerListener {
	void onAddUser(User user);

	void onUpdateUser(User user);

	void onRemoveUser(User user);
}
