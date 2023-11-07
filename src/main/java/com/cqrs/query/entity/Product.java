package com.cqrs.query.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "PRODUCT_QUERY")
public class Product {

    @Id
    @GeneratedValue
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
}
