package com.assigment.ecomscraper.util;

import com.assigment.ecomscraper.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CommonUtil {

    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
    private static final String FILENAME = "list_top_product";
    private static final String CSV_HEADER = "No,Name,Description,ImageLink,PriceInIDR,Rating,Merchant\n";

    public String saveListProductAsCsv(List<Product> products) {
        FileWriter fileWriter;
        log.info("start writing csv file...");
        if (products.isEmpty()) {
            log.info("list of products is null");
            return "Done";
        }
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String formatDateTime = now.format(formatter);
            fileWriter = new FileWriter(FILENAME+"_"+formatDateTime+".csv");
            fileWriter.write(CSV_HEADER);
            AtomicInteger number = new AtomicInteger();
            products.forEach(product -> {
                try {
                    fileWriter.write(String.valueOf(number.get() + 1));
                    fileWriter.write("," + product.getProductName());
                    fileWriter.write("," + product.getDescription());
                    fileWriter.write("," + product.getImagelink());
                    fileWriter.write("," + product.getPrice());
                    fileWriter.write("," + product.getRating());
                    fileWriter.write("," + product.getMerchant() + "\n");
                    number.getAndIncrement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
            log.info("finished writing csv file");
        } catch (IOException ex) {
            log.info("error while writing csv file");
            ex.printStackTrace();
        }

        return "Done";
    }
}
