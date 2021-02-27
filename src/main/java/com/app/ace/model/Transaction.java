package com.app.ace.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Dmitry Bolotin on 2/25/21.
 */
@Data
public class Transaction {
/* Database model
            `TRANSACTION_ID`,
            `TRANSACTION_DATE`,
            `TRANSACTION_TYPE`,
            `TRANSACTION_SUMMARY`,
            `TRANSACTION_AMOUNT`,
            `TRANSACTION_DETAILS`,
            `TRANSACTION_EXPIRY_DATE`,
            `TRANSACTION_PARENT_ID`,
            `FROM_WALLET_ID`,
            `TO_WALLET_ID`
 */

    private long transactionId;
    private LocalDate transactionDate;
    private String transactionType;
    private String transactionSummary;
    private BigDecimal transactionAmount;
    private String transactionDetails;
    private LocalDate transactionExpiryDate;
    private long transactionParentId;
    private String fromWalletId;
    private String toWalletId;
}
