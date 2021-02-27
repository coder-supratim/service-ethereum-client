package com.app.ace.dao;

import com.app.ace.model.Account;
import com.app.ace.model.Transaction;
import com.app.ace.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class DataConnector {

    private static String CONNECTION_URI = "jdbc:mysql:///carrie?cloudSqlInstance=hack-teamcarrie:us-east1:ace-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=dbuser";

   public List<Account> retrieveAccounts() {

       String sqlSelectAllPersons = "SELECT * FROM carrie.ACCOUNT";
        List<Account> accounts = new ArrayList<>();
       try {
           Connection conn = DriverManager.getConnection(CONNECTION_URI, "dbuser", "");
            PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
            ResultSet rs = ps.executeQuery() ;

           while (rs.next()) {
               Account account = new Account();

               String id = rs.getString("ACCOUNT_ID");
               String accountType = rs.getString("ACCOUNT_TYPE");
               String accountName = rs.getString("ACCOUNT_NAME");
               account.setAccountType(accountType);
               account.setAccountName(accountName);
               account.setAccountId(id);
               accounts.add(account);
               log.info("Fetched account for:  {}", account.toString());
           }
       } catch (SQLException e) {
           log.error("Error in connecting database: ", e);
           return accounts;
       }
        return accounts;
    }

    public Account getAccount(String accountId) {

        String queryWalletId = "SELECT AC.ACCOUNT_ID, AC.ACCOUNT_NAME, AC.ACCOUNT_TYPE, AW.WALLET_ID " +
                "FROM carrie.ACCOUNT AS AC " +
                "JOIN carrie.ACCOUNT_WALLET AW ON " +
                " AC.ACCOUNT_ID = AW.ACCOUNT_ID " +
                " and AC.ACCOUNT_ID =? " +
                " JOIN carrie.WALLET WW ON\n" +
                " WW.WALLET_ID = AW.WALLET_ID\n" +
                " AND WW.WALLET_TYPE= 'Person'";

        try {
            Connection conn = DriverManager.getConnection(CONNECTION_URI, "dbuser", "");
            PreparedStatement ps = conn.prepareStatement(queryWalletId);
            ps.setString(1, accountId);
            ResultSet rs = ps.executeQuery() ;

            if (rs.next()) {
                Account account = new Account();
                String walletId = rs.getString("WALLET_ID");
                String id = rs.getString("ACCOUNT_ID");
                String accountType = rs.getString("ACCOUNT_TYPE");
                String accountName = rs.getString("ACCOUNT_NAME");
                account.setAccountType(accountType);
                account.setAccountName(accountName);
                account.setAccountId(id);
                account.setWalletId(walletId);

                log.info("Fetched account for:  {}", account.getAccountName());
                return account;

            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getAllTransactions(String accountId) {

        String sqlSelectAllTransactions = "SELECT * FROM TRANSACTION  WHERE FROM_WALLET_ID IN (SELECT WALLET_ID FROM ACCOUNT_WALLET WHERE ACCOUNT_ID=?) OR " +
                "TO_WALLET_ID IN (SELECT WALLET_ID FROM ACCOUNT_WALLET WHERE ACCOUNT_ID=?)";
        List<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_URI, "dbuser", "");
            PreparedStatement ps = conn.prepareStatement(sqlSelectAllTransactions);
            ps.setString(1, accountId);
            ps.setString(2, accountId);
            ResultSet rs = ps.executeQuery() ;

            while (rs.next()) {
                Transaction transaction = new Transaction();

                transaction.setTransactionId(rs.getLong("TRANSACTION_ID"));
                transaction.setTransactionDate(rs.getDate("TRANSACTION_DATE").toLocalDate());
                transaction.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                transaction.setTransactionSummary(rs.getString("TRANSACTION_SUMMARY"));
                transaction.setTransactionAmount(rs.getBigDecimal("TRANSACTION_AMOUNT"));
                transaction.setTransactionDetails(rs.getString("TRANSACTION_DETAILS"));
                transaction.setTransactionExpiryDate(rs.getDate("TRANSACTION_EXPIRY_DATE").toLocalDate());
                transaction.setTransactionParentId(rs.getLong("TRANSACTION_PARENT_ID"));
                transaction.setFromWalletId(rs.getString("FROM_WALLET_ID"));
                transaction.setToWalletId(rs.getString("TO_WALLET_ID"));
                transactions.add(transaction);
                log.info("Fetched transaction for:  {}", accountId);
            }
        } catch (SQLException e) {
            log.error("Error in connecting database: ", e);
            return transactions;
        }
        return transactions;
    }

    public Wallet getWallet(String accountId) {

        String sqlGetWallet = "SELECT AW.WALLET_ID, WALLET_TYPE, PRIVATE_KEY FROM ACCOUNT_WALLET AW JOIN WALLET WW ON  AW.WALLET_ID = WW.WALLET_ID AND AW.ACCOUNT_ID=?";
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_URI, "dbuser", "");
            PreparedStatement ps = conn.prepareStatement(sqlGetWallet);
            ps.setString(1, accountId);
            ResultSet rs = ps.executeQuery() ;

            if (rs.next()) {
                Wallet wallet = new Wallet();
                wallet.setWalletId(rs.getString("WALLET_ID"));
                wallet.setWalletType(rs.getString("WALLET_TYPE"));
                wallet.setPrivateKey(rs.getString("PRIVATE_KEY"));



                log.info("Fetched WALLET: {} for ACCOUNT_ID:  {}", wallet, accountId);

                return wallet;
            } else return null;
        } catch (SQLException e) {
            log.error("Error in connecting database: ", e);
            throw new RuntimeException(e);
        }
    }
}
