package com.baz.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by arahis on 5/10/17.
 */
@Entity
@Table(name = "clients")
@ToString(exclude = {"id", "accounts"})
@EqualsAndHashCode(exclude = {"id", "accounts"})
@Data public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "birth_date")
    private Date birthDate;

    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Account> accounts;
}
