package com.assigment.ecomscraper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {
    private String productName;
    private String description;
    private String imagelink;
    private String price;
    private String rating;
    private String merchant;
}
