package com.baz.entity;

import com.baz.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by arahis on 5/10/17.
 */
@Entity
@Table(name = "transactions")
@ToString(exclude = {"id", "account"})
@EqualsAndHashCode(exclude = {"id", "account"})
@NoArgsConstructor
@Data public class Transaction {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(name = "balance_before")
    private BigDecimal balanceBefore;
    @Column(name = "balance_after")
    private BigDecimal balanceAfter;
    private String description;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

}
