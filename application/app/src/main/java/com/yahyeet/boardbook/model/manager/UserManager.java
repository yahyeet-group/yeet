package com.yahyeet.boardbook.model.manager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yahyeet.boardbook.model.firebase.FirebaseUserRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;
import com.yahyeet.boardbook.model.util.TestUtil;

public class UserManager {

    private UserManager instance = null;

    private IUserRepository userRepository = null;

    private UserManager(){
        if(TestUtil.isJUnitTest()){
            //TODO add mock or some other solution
            //userRepository =
        }
        else {
            userRepository = new FirebaseUserRepository(FirebaseFirestore.getInstance());
        }
    }

    public UserManager getInstance(){
        if(instance == null) instance = new UserManager();
        return instance;
    }

    public IUserRepository getUserRepository(){
        return userRepository;
    }
}
