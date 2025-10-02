package com.mokaya.tikio.services.user;

import com.mokaya.tikio.models.User;

public interface UserService {
    public User registerUser(String username, String email, String password);

    User findByEmail(String email);

    User findByUsername(String username);
}
