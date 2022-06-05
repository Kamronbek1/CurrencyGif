package com.company.currencygif;

import com.company.currencygif.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableFeignClients
public class CurrencyGifApplication {

    private final ExchangeRatesService exchangeRatesService;

    @Autowired
    public CurrencyGifApplication(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }


    @PostConstruct
    public void postConstructInit() {
        exchangeRatesService.refreshRates();
    }


    public static void main(String[] args) {
        SpringApplication.run(CurrencyGifApplication.class, args);
    }

}
