package com.company.currencygif.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GiphyGifService {

    ResponseEntity<Map> getGif(String tag);
}