package com.ashu.caller.controller;

import com.ashu.caller.model.Response;
import com.ashu.caller.model.UserSearch;
import com.ashu.caller.service.SearchService;
import com.ashu.caller.util.Utility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("name")
    public Response<List<UserSearch>> searchName(@RequestParam String name) {
        return Utility.successResponse(searchService.searchByName(name), "Name Found");
    }

    @GetMapping("mobile")
    public Response<List<UserSearch>> searchNumber(@RequestParam String mobile) {
        return Utility.successResponse(searchService.searchByMobile(mobile), "Number Found");
    }

}
