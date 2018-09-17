package com.ashu.caller.service;

import com.ashu.caller.model.Contact;
import com.ashu.caller.model.User;

import java.util.List;

public interface UserService {

    User updateUser(User user);

    void markNumberSpam(String mobile);

    void uploadContact(List<Contact> contactsList);

    void updateContact(Contact contact);

    void populateDatabaseWithDummy();
}
