package com.baz.dao;

import com.baz.entity.Client;
import com.baz.entity.ExchangeRate;
import com.baz.enums.CurrencyType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by arahis on 5/17/17.
 */
public class ExchangeRateDao {

    public ExchangeRate getRateFor(CurrencyType typeFrom, CurrencyType typeTo) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        TypedQuery<ExchangeRate> query = em.createQuery("SELECT r FROM ExchangeRate r " +
                "WHERE r.source = :typeFrom " +
                "and r.target = :typeTo", ExchangeRate.class);
        query.setParameter("typeFrom", typeFrom);
        query.setParameter("typeTo", typeTo);

        ExchangeRate er = query.getSingleResult();

        em.close();

        return er;
    }
}
