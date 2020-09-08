package com.demo.kmd.service;

import com.demo.kmd.models.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean doesUserExist(String userId){
        return true;
    }

    public User createUser(User user){
        return null;
    }

    public User updateUser(User user){
        return null;
    }

    public boolean isUserBlocked(String userId){
        return false;
    }

    public boolean deleteUser(String userId){
        return true;
    }
}
