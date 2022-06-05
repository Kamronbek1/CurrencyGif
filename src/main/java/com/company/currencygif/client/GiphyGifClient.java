package com.company.currencygif.client;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GiphyGifClient {

    ResponseEntity<Map> getRandomGif(String apiKey, String tag);

}
