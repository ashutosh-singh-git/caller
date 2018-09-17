package com.ashu.caller.service.impl;

import com.ashu.caller.dao.entity.UserSessionEntity;
import com.ashu.caller.service.CurrentUserService;

public class CurrentUserServiceImpl implements CurrentUserService {

    private UserSessionEntity userEntity;

    @Override
    public void setUserInfo(UserSessionEntity user) {
        this.userEntity = user;
    }

    @Override
    public UserSessionEntity getUserInfo() {
        return this.userEntity;
    }
}
