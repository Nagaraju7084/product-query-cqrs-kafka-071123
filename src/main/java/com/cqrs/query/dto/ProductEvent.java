package com.cqrs.query.dto;

import com.cqrs.query.entity.Product;
import lombok.Data;

@Data
public class ProductEvent {
    private String eventType;
    private Product product;
}
