package com.company.currencygif.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Feign client for getting random gifs from giphy.com api
 */

@FeignClient(name = "GiphyClient", url = "${giphy.url}")
public interface FeignGiphyGifClient extends GiphyGifClient {

    @Override
    @GetMapping("/random")
    ResponseEntity<Map> getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );

}