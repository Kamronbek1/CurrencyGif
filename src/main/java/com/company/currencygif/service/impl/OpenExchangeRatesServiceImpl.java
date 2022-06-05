package com.company.currencygif.service.impl;

import com.company.currencygif.client.OpenExchangeRatesClient;
import com.company.currencygif.model.ExchangeRatesDto;
import com.company.currencygif.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class OpenExchangeRatesServiceImpl implements ExchangeRatesService {

    private ExchangeRatesDto prevRates;
    private ExchangeRatesDto currentRates;

    private final OpenExchangeRatesClient openExchangeRatesClient;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat timeFormat;
    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    @Autowired
    public OpenExchangeRatesServiceImpl(
            OpenExchangeRatesClient openExchangeRatesClient,
            @Qualifier("date_bean") SimpleDateFormat dateFormat,
            @Qualifier("time_bean") SimpleDateFormat timeFormat
    ) {
        this.openExchangeRatesClient = openExchangeRatesClient;
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
    }

    /**
     * Возвращает список доступных для проверки валют.
     *
     * @return
     */
    @Override
    public List<String> getCharCodes() {
        List<String> result = null;
        if (this.currentRates.getRates() != null) {
            result = new ArrayList<>(this.currentRates.getRates().keySet());
        }
        return result;
    }

    @Override
    public int getKeyForTag(String charCode) {
        this.refreshRates();
        Double prevCoefficient = this.getCoefficient(this.prevRates, charCode);
        Double currentCoefficient = this.getCoefficient(this.currentRates, charCode);
        return prevCoefficient != null && currentCoefficient != null
                ? Double.compare(currentCoefficient, prevCoefficient) : -101;
    }

    @Override
    public void refreshRates() {
        long currentTime = System.currentTimeMillis();
        this.refreshCurrentRates(currentTime);
        this.refreshPrevRates(currentTime);
    }

    private void refreshCurrentRates(long time) {
        if (this.currentRates == null || !timeFormat.format(Long.valueOf(this.currentRates.getTimestamp()) * 1000)
                .equals(timeFormat.format(time))) {
            this.currentRates = openExchangeRatesClient.getLatestRates(this.appId);
        }
    }

    private void refreshPrevRates(long time) {
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTimeInMillis(time);
        String currentDate = dateFormat.format(prevCalendar.getTime());
        prevCalendar.add(Calendar.DAY_OF_YEAR, -1);
        String newPrevDate = dateFormat.format(prevCalendar.getTime());
        if (this.prevRates == null || (!dateFormat.format(Long.valueOf(this.prevRates.getTimestamp()) * 1000)
                .equals(newPrevDate)
                && !dateFormat.format(Long.valueOf(this.prevRates.getTimestamp()) * 1000)
                .equals(currentDate))) {
            this.prevRates = openExchangeRatesClient.getHistoricalRates(newPrevDate, appId);
        }
    }

    private Double getCoefficient(ExchangeRatesDto rates, String charCode) {
        Double result = null;
        Double targetRate = null;
        Double appBaseRate = null;
        Double defaultBaseRate = null;
        Map<String, Double> map = null;
        if (rates != null && rates.getRates() != null) {
            map = rates.getRates();
            targetRate = map.get(charCode);
            appBaseRate = map.get(this.base);
            defaultBaseRate = map.get(rates.getBase());
        }
        if (targetRate != null && appBaseRate != null && defaultBaseRate != null) {
            result = new BigDecimal((defaultBaseRate / appBaseRate) * targetRate)
                    .setScale(4, RoundingMode.UP)
                    .doubleValue();
        }
        return result;
    }

}
