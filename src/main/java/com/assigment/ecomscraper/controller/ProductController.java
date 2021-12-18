package com.assigment.ecomscraper.controller;

import com.assigment.ecomscraper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/scraper")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public @ResponseBody String collectProductData() throws IOException {
        return productService.collectProduct();
    }
}
