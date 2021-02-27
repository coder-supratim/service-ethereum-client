package com.app.ace.controller;

import com.app.ace.dao.DataConnector;
import com.app.ace.model.Account;
import com.app.ace.model.Award;
import com.app.ace.model.Transaction;
import com.app.ace.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ace-service")
@AllArgsConstructor
public class AceController {

    private final AccountService accountService;

    private final DataConnector dataConnector;

    @CrossOrigin
    @GetMapping(value = "/account/{accountId}", produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId){
       log.info("Ethereum Service - getAccount API invoked, accountId: {}", accountId);

       return ResponseEntity.ok(accountService.getAccountDetails(accountId));
    }

    @CrossOrigin
    @GetMapping(value = "/transaction/{accountId}", produces = "application/json")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountId){
        log.info("Ethereum Service Get All Transaction API invoked for accountId: {}", accountId);

        return ResponseEntity.ok(dataConnector.getAllTransactions(accountId));
    }

    @CrossOrigin
    @PostMapping(value = "/award", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> giveAward(@RequestBody Award award){
        log.info("Ethereum Service give Award API invoked for award: {}", award);

        accountService.processAwardTransaction(award);

        return ResponseEntity.ok("Ok");
    }


}
