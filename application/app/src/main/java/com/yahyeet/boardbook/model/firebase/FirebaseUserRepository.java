package com.yahyeet.boardbook.model.firebase;

import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IUserRepository;

public class FirebaseUserRepository implements IUserRepository {
    @Override
    public void Add(User user) {
    }

    @Override
    public User Find(String id) {
        // Changed to be able to run code, was return void
        return null;
    }

    @Override
    public void Remove(User user) {

    }

    @Override
    public void Update(User user) {

    }

}
