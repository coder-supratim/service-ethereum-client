package com.app.ace.service.impl;

import com.app.ace.config.AccountServiceConfig;
import com.app.ace.dao.DataConnector;
import com.app.ace.model.Account;
import com.app.ace.model.Award;
import com.app.ace.model.Wallet;
import com.app.ace.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountServiceConfig accountServiceConfig;
    @Value("${ethereum.client.endpoint}")
    private String ethereumClientEndpoint;
    @Value("${ethereum.transaction.endpoint}")
    private String ethereumDoTransactionTemplate;

    @Autowired
    DataConnector dataConnector;

    @Override
    public Account getAccountDetails(String accountId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        Account account = dataConnector.getAccount(accountId);

        log.info("Ethereum Client: {}", ethereumClientEndpoint + account.getWalletId());

        ResponseEntity<AccountResponse> accountResponseResponseEntityResponse = accountServiceConfig.getRestTemplate()
                .exchange(ethereumClientEndpoint + account.getWalletId(), HttpMethod.GET, httpEntity, AccountResponse.class);
        account.setAccountBalance(accountResponseResponseEntityResponse.getBody().getBalance());

        log.info("Account Balance fetched from Ethereum Client: {}", accountResponseResponseEntityResponse.toString());

        log.info("Account details fetched for {}", accountId);
        return account;
    }

    @Override
    public void processAwardTransaction(Award award) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        Wallet fromWallet = dataConnector.getWallet(award.getOriginatorAccountId());
        Wallet toWallet = dataConnector.getWallet(award.getTargetAccountId());

        log.info("Preparing transaction from wallet {} to wallet {}", fromWallet.getWalletId(), toWallet.getWalletId());
        String finalCall = ethereumDoTransactionTemplate
                .replace("$FROM", fromWallet.getWalletId())
                .replace("$TO", toWallet.getWalletId())
                .replace("$AMOUNT", award.getAmount().toString())
                .replace("$KEY", fromWallet.getPrivateKey());

        log.info("Exact endpoint to call is {}", finalCall);


        ResponseEntity<AccountResponse[]> response = accountServiceConfig.getRestTemplate().exchange(finalCall, HttpMethod.GET, httpEntity, AccountResponse[].class);

    }

    private Account mockResponse(String accountId) {
        Account mockAccount = new Account();
        mockAccount.setAccountId(accountId);
        mockAccount.setAccountName("Tim's ACE Wallet");
        mockAccount.setAccountType("Group");

        return mockAccount;

    }
}
