package com.company.currencygif.client;


import com.company.currencygif.model.ExchangeRatesDto;

public interface OpenExchangeRatesClient {

    ExchangeRatesDto getLatestRates(String appId);

    ExchangeRatesDto getHistoricalRates(String date, String appId);
}
