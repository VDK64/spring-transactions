package com.example.transactions.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
@Data
public class Person {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private int account;

}
