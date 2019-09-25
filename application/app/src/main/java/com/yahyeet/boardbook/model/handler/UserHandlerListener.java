package com.yahyeet.boardbook.model.handler;

import com.yahyeet.boardbook.model.entity.User;

public interface UserHandlerListener {
    void onAddUser(User user);

    void onUpdateUser(User user);

    void onRemoveUser(User user);
}
