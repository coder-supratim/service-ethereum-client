package com.app.ace.service;


import com.app.ace.model.Account;
import com.app.ace.model.Award;

import java.util.List;

public interface AccountService {

    Account getAccountDetails (String accountId);

    void processAwardTransaction(Award award);

}
