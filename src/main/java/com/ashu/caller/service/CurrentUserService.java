package com.ashu.caller.service;

import com.ashu.caller.dao.entity.UserSessionEntity;

public interface CurrentUserService {

    void setUserInfo(UserSessionEntity user);

    UserSessionEntity getUserInfo();
}
