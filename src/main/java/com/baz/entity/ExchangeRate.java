package com.baz.entity;

import com.baz.enums.CurrencyType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by arahis on 5/10/17.
 */
@Entity
@Table(name = "exchange_rates")
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@Data public class ExchangeRate {
    @Id
    @GeneratedValue
    private long id;

    private CurrencyType source;
    private CurrencyType target;
    private BigDecimal rate;

    public ExchangeRate(CurrencyType source, CurrencyType target, BigDecimal rate) {
        this.source = source;
        this.target = target;
        this.rate = rate;
    }
}
