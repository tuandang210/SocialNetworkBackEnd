package com.codegym.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePasswordAccount {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
