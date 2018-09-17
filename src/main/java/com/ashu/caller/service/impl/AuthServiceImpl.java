package com.ashu.caller.service.impl;

import com.ashu.caller.dao.entity.UserSessionEntity;
import com.ashu.caller.dao.repository.UserRepository;
import com.ashu.caller.dao.repository.UserSessionRepository;
import com.ashu.caller.exception.CallerException;
import com.ashu.caller.model.LoginRequest;
import com.ashu.caller.service.AuthService;
import com.ashu.caller.service.CurrentUserService;
import com.ashu.caller.util.DtoUtil;
import com.ashu.caller.dao.entity.UserEntity;
import com.ashu.caller.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final CurrentUserService currentUser;
    private final static int OTP = 1234;

    public AuthServiceImpl(UserRepository userRepository, UserSessionRepository userSessionRepository,
                           CurrentUserService currentUser) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.currentUser = currentUser;
    }

    @Override
    public User register(User user) {
        UserEntity userEntity = DtoUtil.convertByCopy(user, UserEntity.class);

        userEntity.setCreatedAt(new Date());
        userEntity.setUpdatedAt(new Date());
        try {
            return DtoUtil.convertByCopy(userRepository.save(userEntity), User.class);
        } catch (DataIntegrityViolationException e) {
            throw new CallerException("User Already exists!");
        }
    }

    @Override
    public String login(LoginRequest loginRequest) {

        UserEntity userEntity = userRepository.findByMobile(loginRequest.getMobile());

        if (userEntity != null && loginRequest.getOtp() == OTP) {

            String token = String.valueOf(UUID.randomUUID());
            UserSessionEntity userSessionEntity = new UserSessionEntity();
            userSessionEntity.setLastLogin(new Date());
            userSessionEntity.setUser(userEntity);
            userSessionEntity.setToken(token);
            userSessionRepository.saveAndFlush(userSessionEntity);
            return token;

        } else {
            throw new CallerException("OTP or mobile number is incorrect");
        }

    }

    @Override
    public boolean generateOtp(String mobile) {
        UserEntity userEntity = userRepository.findByMobile(mobile);

        if (userEntity == null) {
            throw new CallerException("Mobile Number is not present");
        }
        return true;
    }

    @Override
    public void logout(String mobile) {
        UserEntity userEntity = userRepository.findByMobile(mobile);
        if (userEntity != null) {
            userSessionRepository.deleteById(userEntity.getUserId());
        }
    }

    @Override
    public boolean validateToken(String token) {
        UserSessionEntity userSessionEntity = userSessionRepository.findByToken(token);
        if(userSessionEntity != null && !StringUtils.isEmpty(userSessionEntity.getToken())) {
            currentUser.setUserInfo(userSessionEntity);
            return true;
        }
        return false;
    }
}
