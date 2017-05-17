package com.baz.dao;

import com.baz.entity.Client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by arahis on 5/11/17.
 */
public class ClientDao {

    public Client getClientById(long id) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> clientRoot = cq.from(Client.class);
        cq.select(clientRoot)
                .where(cb.equal(clientRoot.get("id"), id));

        Client client = em.createQuery(cq).getSingleResult();
        em.close();

        return client;
    }

    public void save(Client client) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();
        em.close();
    }
}
