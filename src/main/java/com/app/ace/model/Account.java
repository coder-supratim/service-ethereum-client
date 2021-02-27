package com.app.ace.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account    {

    private String accountId;

    private String accountName;

    private String accountType;

    private BigDecimal accountBalance;

    private String walletId;
}
