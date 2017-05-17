package com.baz.entity;

import com.baz.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by arahis on 5/10/17.
 */
@Entity
@Table(name = "accounts")
@ToString(exclude = {"id", "client", "transactions"})
@EqualsAndHashCode(exclude = {"id", "client", "transactions"})
@Data public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private CurrencyType type;
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}
