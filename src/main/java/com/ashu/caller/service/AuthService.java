package com.ashu.caller.service;

import com.ashu.caller.model.LoginRequest;
import com.ashu.caller.model.User;

public interface AuthService {

    User register(User user);

    String login(LoginRequest loginRequest);

    boolean generateOtp(String mobile);

    void logout(String mobile);

    boolean validateToken(String token);
}
