package com.baz.service;

import com.baz.dao.ClientDao;
import com.baz.dao.ExchangeRateDao;
import com.baz.dao.TransactionDao;
import com.baz.entity.Account;
import com.baz.entity.Client;
import com.baz.entity.ExchangeRate;
import com.baz.entity.Transaction;
import com.baz.enums.CurrencyType;
import com.baz.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by arahis on 5/16/17.
 */
public class ClientService {

    private ClientDao clientDao = new ClientDao();
    private ExchangeRateDao exchangeRateDao = new ExchangeRateDao();
    private TransactionDao transactionDao = new TransactionDao();

    public void replenishAccount(long clientId, CurrencyType accountType, BigDecimal amount) {
        Client client = clientDao.getClientById(clientId);

        Account acc = MoneyOperationUtils.getAccByType(client, accountType);
        Transaction trans = newTransactionForAcc(TransactionType.REPLENISH,
                "account replenishment", acc);

        MoneyOperationUtils.addToClient(client, accountType, amount);

        trans.setBalanceAfter(acc.getBalance());

        transactionDao.save(trans);
        clientDao.save(client);
    }

    public void transfer(long clientIdFrom, long clientIdTo,
                         CurrencyType accountType, BigDecimal amount) {
        Client clientFrom = clientDao.getClientById(clientIdFrom);
        Client clientTo = clientDao.getClientById(clientIdTo);


        Account acc = MoneyOperationUtils.getAccByType(clientFrom, accountType);
        Transaction trans = newTransactionForAcc(TransactionType.TRANSFER,
                "money transfer", acc);

        MoneyOperationUtils.takeFromClient(clientFrom, accountType, amount);
        MoneyOperationUtils.addToClient(clientTo, accountType, amount);

        trans.setBalanceAfter(acc.getBalance());

        transactionDao.save(trans);
        clientDao.save(clientFrom);
        clientDao.save(clientTo);
    }

    public void exchange(long clientId, CurrencyType typeFrom,
                         CurrencyType typeTo, BigDecimal amount) {
        Client client = clientDao.getClientById(clientId);
        ExchangeRate exchangeRate = exchangeRateDao.getRateFor(typeFrom, typeTo);

        Account acc = MoneyOperationUtils.getAccByType(client, typeFrom);
        Transaction trans = newTransactionForAcc(TransactionType.EXCHANGE,
                "money exchanging to account: " + typeTo, acc);

        MoneyOperationUtils.exchange(client, exchangeRate, amount);

        trans.setBalanceAfter(acc.getBalance());

        transactionDao.save(trans);
        clientDao.save(client);
    }

    public void withdrawAllInUAH(long clientId) {
        Client client = clientDao.getClientById(clientId);

        for (Account acc : client.getAccounts()) {
            CurrencyType accType = acc.getType();
            if (accType != CurrencyType.UAH) {
                ExchangeRate rateForUAH =
                        exchangeRateDao.getRateFor(accType, CurrencyType.UAH);
                BigDecimal balance = acc.getBalance();
                BigDecimal convertedBalance = balance.multiply(rateForUAH.getRate());

                Transaction trans = newTransactionForAcc(TransactionType.WITHDRAW,
                        "withdrawong all currency to account: " + CurrencyType.UAH, acc);

                MoneyOperationUtils.takeFromClient(client, accType, balance);
                MoneyOperationUtils.addToClient(client, CurrencyType.UAH, convertedBalance);

                trans.setBalanceAfter(acc.getBalance());
                transactionDao.save(trans);
            }
        }

        clientDao.save(client);
    }

    private Transaction newTransactionForAcc(TransactionType type,
                                             String description, Account acc) {
        Transaction trans = new Transaction();
        trans.setType(type);
        trans.setDate(new Date());
        trans.setDescription(description);
        trans.setAccount(acc);
        trans.setBalanceBefore(acc.getBalance());

        return trans;
    }
}
