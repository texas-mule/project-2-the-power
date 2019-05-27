package com.project2.stockConsumerApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.project2.stockConsumerResources.Stock;

public class getPrice {

    private static final Logger log = LoggerFactory.getLogger(getPrice.class);

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        Stock quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Stock.class);
        log.info(quote.toString());
    }

}