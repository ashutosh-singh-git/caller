package com.ashu.caller.service;

import com.ashu.caller.model.UserSearch;

import java.util.List;

public interface SearchService {

    List<UserSearch> searchByMobile(String mobile);

    List<UserSearch> searchByName(String name);

}
