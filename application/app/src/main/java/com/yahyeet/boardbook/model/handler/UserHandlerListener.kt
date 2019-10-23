package com.yahyeet.boardbook.model.handler

import com.yahyeet.boardbook.model.entity.User

interface UserHandlerListener {
    fun onAddUser(user: User)

    fun onUpdateUser(user: User)

    fun onRemoveUser(user: User)
}
