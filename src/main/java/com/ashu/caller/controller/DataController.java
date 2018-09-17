package com.ashu.caller.controller;

import com.ashu.caller.model.Response;
import com.ashu.caller.service.UserService;
import com.ashu.caller.util.Utility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataController {

    private final UserService userService;

    public DataController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("populate")
    public Response<String> populateDummyData() {
        userService.populateDatabaseWithDummy();
        return Utility.successResponse("Accepted");
    }

}
