package com.assigment.ecomscraper.service;

import com.assigment.ecomscraper.model.Product;
import com.assigment.ecomscraper.util.CommonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private CommonUtil commonUtil;

    private Logger log = LoggerFactory.getLogger(ProductService.class);

    @Value("${target.uri}")
    private String TARGET_URI;
    @Value("${redirection.uri}")
    private String REDIR_URI;
    @Value("${element.key.mainpage}")
    private String ELM_KEY_MAINPAGE;
    @Value("${element.value.mainpage}")
    private String ELM_VALUE_MAINPAGE;
    @Value("${element.key}")
    private String ELM_KEY;
    @Value("${element.value.product.name}")
    private String ELM_VAL_PROD_NAME;
    @Value("${element.value.product.description}")
    private String ELM_VAL_PROD_DESC;
    @Value("${element.value.product.image}")
    private String ELM_VAL_PROD_IMG;
    @Value("${element.value.product.price}")
    private String ELM_VAL_PROD_PRICE;
    @Value("${element.key.product.rating}")
    private String ELM_KEY_RATING;
    @Value("${element.value.product.rating}")
    private String ELM_VAL_PROD_RATING;

    private static int MAX = 100;

    public String collectProduct() throws IOException {
        log.info("start scraping...");
        List<Product> response = new ArrayList<>();

        int currentPage = 1;

        while (response.size() < MAX) {
            String currentPageUri = TARGET_URI + currentPage;
//            log.info("scraping data from " + currentPageUri);

            Document mainPage = Jsoup.connect(currentPageUri).get();

            Element productListElm = mainPage.getElementsByAttributeValue(ELM_KEY_MAINPAGE, ELM_VALUE_MAINPAGE).get(0);
            Elements productLinksElm = productListElm.select("div > a");

            for (Element linkElm : productLinksElm) {
                String productLink = linkElm.attr("href");

                if (productLink.contains(REDIR_URI))
                    continue;

                Document productPage = Jsoup.connect(productLink).get();
//                log.info("product " + productLink);

                String productName = productPage.getElementsByAttributeValue(ELM_KEY, ELM_VAL_PROD_NAME).get(0).text();
                String sanitizeProductName = productName.replaceAll(",", "+");
                String productDesc = "NA";
                if (!productPage.getElementsByAttributeValue(ELM_KEY, ELM_VAL_PROD_DESC).isEmpty())
                    productDesc = productPage.getElementsByAttributeValue(ELM_KEY, ELM_VAL_PROD_DESC).get(0).text();
                String sanitizedProductDesc = productDesc.replaceAll(",", "+");
                Element productImageElm = productPage.getElementsByAttributeValue(ELM_KEY, ELM_VAL_PROD_IMG).get(0);
                String productImageLink = productImageElm.select("img").first().attr("src");
                String productPrice = productPage.getElementsByAttributeValue(ELM_KEY, ELM_VAL_PROD_PRICE).get(0).text();
                String sanitizedProductPrice = productPrice.replaceAll("[Rp.]","");
                String productRating = productPage.getElementsByAttributeValue(ELM_KEY_RATING, ELM_VAL_PROD_RATING).get(0).attr("content");
                String merchantName = productPage
                        .getElementsByAttributeValue("property", "og:url")
                        .get(0)
                        .attr("content")
                        .split("/")[3];

                Product product = Product.builder()
                        .productName(sanitizeProductName)
                        .description(sanitizedProductDesc)
                        .imagelink(productImageLink)
                        .price(sanitizedProductPrice)
                        .rating(productRating)
                        .merchant(merchantName).build();

                response.add(product);

                if (response.size() == MAX)
                    break;
            }
            currentPage++;
        }

        return commonUtil.saveListProductAsCsv(response);
    }

}
