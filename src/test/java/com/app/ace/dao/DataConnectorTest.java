package com.app.ace.dao;

import com.app.ace.app.EthereumClientApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Dmitry Bolotin on 2/25/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EthereumClientApplication.class})
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