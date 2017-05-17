package com.baz.dao;

import com.baz.entity.Client;
import com.baz.entity.Transaction;

import javax.persistence.EntityManager;

/**
 * Created by arahis on 5/17/17.
 */
public class TransactionDao {

    public void save(Transaction trans) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        em.merge(trans);
        em.getTransaction().commit();
        em.close();
    }
}
