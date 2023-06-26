package com.example.userservice.vo;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {

    @NotNull(message = "필수입력 항목입니다.")
    @Size(min = 2, message = "2글자 이상이어야 합니다.")
    @Email
    private String email;
    @NotNull(message = "필수입력 항목입니다.")
    @Size(min = 8, message = "8글자 이상이어야 합니다.")
    private String password;
}
