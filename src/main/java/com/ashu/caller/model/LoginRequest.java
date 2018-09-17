package com.ashu.caller.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {

    @NotNull
    @Max(9999999999L)
    private String mobile;
    @NotNull
    private int otp;

}
