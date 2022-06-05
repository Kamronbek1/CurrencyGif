package com.company.currencygif.service.impl;

import com.company.currencygif.client.GiphyGifClient;
import com.company.currencygif.service.GiphyGifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GiphyGifServiceImpl implements GiphyGifService {

    private final GiphyGifClient giphyGifClient;
    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GiphyGifServiceImpl(GiphyGifClient giphyGifClient) {
        this.giphyGifClient = giphyGifClient;
    }

    @Override
    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> result = giphyGifClient.getRandomGif(this.apiKey, tag);
        result.getBody().put("compareResult", tag);
        return result;
    }
}
