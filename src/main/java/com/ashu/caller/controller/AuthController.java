package com.ashu.caller.controller;

import com.ashu.caller.model.LoginRequest;
import com.ashu.caller.model.Response;
import com.ashu.caller.service.AuthService;
import com.ashu.caller.model.User;
import com.ashu.caller.util.Utility;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public Response<User> registerUser(@RequestBody @Valid User user) {
        return Utility.successResponse(authService.register(user), "Registration Successful");
    }

    @PostMapping("otp")
    public Response<String> generateOtp(@RequestParam String mobile) {
        return authService.generateOtp(mobile) ? Utility.successResponse("Otp sent to registered mobile number")
                : Utility.failureResponse("Mobile no not present");
    }

    @PostMapping("login")
    public Response<String> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return Utility.successResponse(authService.login(loginRequest), "Login Successful");
    }

    @PostMapping("logout")
    public Response<String> logoutUser(@RequestParam String mobile) {
        authService.logout(mobile);
        return Utility.successResponse("logged out");
    }

}
