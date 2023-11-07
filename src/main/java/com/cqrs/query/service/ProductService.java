package com.cqrs.query.service;

import com.cqrs.query.dto.ProductDto;
import com.cqrs.query.entity.Product;
import com.cqrs.query.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
