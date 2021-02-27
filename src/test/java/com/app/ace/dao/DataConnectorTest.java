package com.app.ace.dao;

import com.app.ace.app.AccessibilityCoinExchangeApplication;
import com.app.ace.controller.AceController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Dmitry Bolotin on 2/25/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AccessibilityCoinExchangeApplication.class})
class DataConnectorTest {

    @Autowired
    private DataConnector dataConnector;

    //@Test
    void getAllTransactions() {
        dataConnector.getAllTransactions("jennie.macko@db.com").forEach(System.out::println);
    }

    //@Test
    void getWallet() {
        dataConnector.getWallet("jennie.macko@db.com");
    }

    //@Test
    void getAccount() {
        dataConnector.getAccount("jennie.macko@db.com");
    }
}