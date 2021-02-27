package com.app.ace.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Dmitry Bolotin on 2/25/21.
 */
@Data
public class Award {

    private String originatorAccountId;
    private String targetAccountId;
    private BigDecimal amount;
    private String description;
    private String tags;
}
