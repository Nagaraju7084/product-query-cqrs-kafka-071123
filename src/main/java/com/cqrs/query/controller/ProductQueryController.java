package com.cqrs.query.controller;

import com.cqrs.query.dto.ProductDto;
import com.cqrs.query.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductQueryController {

    @Autowired
    private ProductService productService;
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

}
