package com.baz.dao;

import com.baz.entity.Account;
import com.baz.entity.Client;
import com.baz.entity.ExchangeRate;
import com.baz.enums.CurrencyType;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.baz.enums.CurrencyType.*;


/**
 * Created by arahis on 5/9/17.
 */
public enum PersistenceManager {
    INSTANCE;

    private EntityManagerFactory emFactory;

    PersistenceManager() {
        emFactory = Persistence.createEntityManagerFactory("h1");

        autofillDb();
    }

    private void autofillDb() {
        fillWithClients();
        fillWithExchangeRates();
    }

    private void fillWithExchangeRates() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            ExchangeRate erUAHtoUSD = new ExchangeRate(UAH, USD, new BigDecimal("0.038"));
            ExchangeRate erUAHtoEUR = new ExchangeRate(UAH, EUR, new BigDecimal("0.034"));

            ExchangeRate erUSDtoUAH = new ExchangeRate(USD, UAH, new BigDecimal("26.41"));
            ExchangeRate erUSDtoEUR = new ExchangeRate(USD, EUR, new BigDecimal("0.90"));

            ExchangeRate erEURtoUAH = new ExchangeRate(EUR, UAH, new BigDecimal("29.34"));
            ExchangeRate erEURtoUSD = new ExchangeRate(EUR, USD, new BigDecimal("1.11"));

            em.persist(erUAHtoUSD);
            em.persist(erUAHtoEUR);

            em.persist(erUSDtoUAH);
            em.persist(erUSDtoEUR);

            em.persist(erEURtoUAH);
            em.persist(erEURtoUSD);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private void fillWithClients() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            for (int i = 0; i < 5; i++) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date birthDate = dateFormat.parse("29-10-194" + i);
                Client client = new Client();
                client.setName("Bob" + i);
                client.setLastname("Ross" + i);
                client.setBirthDate(birthDate);

                List<Account> accounts = new ArrayList<>();
                Account accUAH = new Account();
                accUAH.setClient(client);
                accUAH.setType(CurrencyType.UAH);
                accUAH.setBalance(new BigDecimal("0"));

                Account accUSD = new Account();
                accUSD.setClient(client);
                accUSD.setType(CurrencyType.USD);
                accUSD.setBalance(new BigDecimal("15"));

                Account accEUR = new Account();
                accEUR.setClient(client);
                accEUR.setType(CurrencyType.EUR);
                accEUR.setBalance(new BigDecimal("10"));

                accounts.add(accUAH);
                accounts.add(accUSD);
                accounts.add(accEUR);

                client.setAccounts(accounts);
                em.persist(client);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public void close() {
        emFactory.close();
    }
}
