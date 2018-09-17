package com.ashu.caller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response<T> {

    private boolean status;
    private String message;
    private T data;
}
