package com.company.currencygif.client;

import com.company.currencygif.model.ExchangeRatesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OpenExchangeRatesClient", url = "${openexchangerates.url}")
public interface FeignOpenExchangeRatesClient extends OpenExchangeRatesClient {

    @Override
    @GetMapping("/latest.json")
    ExchangeRatesDto getLatestRates(@RequestParam("app_id") String appId);

    @Override
    @GetMapping("/historical/{date}.json")
    ExchangeRatesDto getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String appId);
}
