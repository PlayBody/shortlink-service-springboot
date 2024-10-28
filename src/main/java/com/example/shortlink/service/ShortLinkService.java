package com.example.shortlink.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ShortLinkService {
    private final Map<String, String> urlMap = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final String baseUrl = "http://short.est/";

    public String encode(String originalUrl) {
        int id = counter.incrementAndGet();
        String shortUrl = baseUrl + Integer.toHexString(id);
        urlMap.put(shortUrl, originalUrl);
        return shortUrl;
    }

    public String decode(String shortUrl) {
        return urlMap.getOrDefault(shortUrl, "URL not found");
    }
}