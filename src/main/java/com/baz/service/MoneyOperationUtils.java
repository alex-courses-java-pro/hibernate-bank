package com.baz.service;

import com.baz.entity.Account;
import com.baz.entity.Client;
import com.baz.entity.ExchangeRate;
import com.baz.enums.CurrencyType;

import java.math.BigDecimal;

/**
 * Created by arahis on 5/17/17.
 */
public class MoneyOperationUtils {


    public static void addToClient(Client clientTo, CurrencyType type, BigDecimal amount) {
        Account acc = getAccByType(clientTo, type);
        BigDecimal currentBalance = acc.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        acc.setBalance(newBalance);
    }

    public static void takeFromClient(Client clientFrom, CurrencyType type, BigDecimal amount) {
        Account acc = getAccByType(clientFrom, type);
        BigDecimal currentBalance = acc.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amount);
        acc.setBalance(newBalance);
    }

    public static void exchange(Client client, ExchangeRate exchangeRate, BigDecimal amount) {
        takeFromClient(client, exchangeRate.getSource(), amount);

        BigDecimal rate = exchangeRate.getRate();
        BigDecimal convertedAmount = amount.multiply(rate);

        addToClient(client, exchangeRate.getTarget(), convertedAmount);
    }

    public static Account getAccByType(Client client, CurrencyType type) {
        Account account = null;
        for (Account acc : client.getAccounts())
            if (acc.getType() == type)
                account = acc;
        return account;
    }
}
