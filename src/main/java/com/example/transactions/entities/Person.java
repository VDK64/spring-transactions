package com.example.transactions.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table
@Data
public class Person {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private int age;

}
