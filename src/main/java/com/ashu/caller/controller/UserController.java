package com.ashu.caller.controller;

import com.ashu.caller.model.Contact;
import com.ashu.caller.util.FirstGroup;
import com.ashu.caller.model.Response;
import com.ashu.caller.model.User;
import com.ashu.caller.service.UserService;
import com.ashu.caller.util.Utility;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("update")
    public Response<User> updateUser(@RequestBody @Validated(FirstGroup.class) User user) {
        return Utility.successResponse(userService.updateUser(user), "User Updated");
    }

    @PostMapping("spam")
    public Response<User> updateUser(@RequestBody String mobile) {
        userService.markNumberSpam(mobile);
        return Utility.successResponse("Marked User a Spam");
    }

    @PostMapping("upload/contacts")
    public Response<String> uploadContacts(@RequestBody @Valid List<Contact> contacts) {
        userService.uploadContact(contacts);
        return Utility.successResponse("Successfully uploaded Contacts");
    }

    @PostMapping("update/contact")
    public Response<String> updateContact(@RequestBody @Validated(FirstGroup.class) Contact contact) {
        userService.updateContact(contact);
        return Utility.successResponse("Successfully updated Contacts");
    }

}
