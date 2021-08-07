package com.codegym.model.dto;

import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {
    private Long id;

    private String content;

    private Account account;

    private Privacy privacy;

    private String url;
}
