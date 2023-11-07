package com.cqrs.query.service;

import com.cqrs.query.dto.ProductDto;
import com.cqrs.query.dto.ProductEvent;
import com.cqrs.query.entity.Product;
import com.cqrs.query.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts(){
       return productRepository.findAll().stream().map(
                product -> entityToDto(product)
        ).collect(Collectors.toList());
    }

    @KafkaListener(topics = "product-event-topic", groupId = "product-event-group",
            properties= {"spring.json.value.default.type=com.cqrs.query.dto.ProductEvent"})
    public void processProductEvent(ProductEvent productEvent){
        Product product = productEvent.getProduct();
        if(productEvent.getEventType().equals("CreateProduct"))
            productRepository.save(product);
        if(productEvent.getEventType().equals("UpdateProduct")) {
            Product dbProduct = productRepository.findById(product.getProductId()).get();
            dbProduct.setProductId(product.getProductId());
            dbProduct.setProductName(product.getProductName());
            dbProduct.setProductDescription(product.getProductDescription());
            dbProduct.setProductPrice(product.getProductPrice());
            productRepository.save(dbProduct);
        }
    }
    private Product dtoToEntity(ProductDto productDto){
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductPrice(productDto.getProductPrice());
        return product;
    }

    private ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
}
