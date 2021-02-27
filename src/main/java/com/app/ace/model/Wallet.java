package com.app.ace.model;

import lombok.Data;

/**
 * Created by Dmitry Bolotin on 2/26/21.
 */
@Data
public class Wallet {
    private String walletId;
    private String walletType;
    private String privateKey;
}
