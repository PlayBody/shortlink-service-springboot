package com.example.shortlink.controller;

import com.example.shortlink.service.ShortLinkService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService shortLinkService) {
        this.shortLinkService = shortLinkService;
    }

    @PostMapping("/encode")
    public Map<String, String> encode(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortUrl = shortLinkService.encode(originalUrl);
        return Map.of("shortUrl", shortUrl);
    }

    @GetMapping("/decode")
    public Map<String, String> decode(@RequestParam String shortUrl) {
        String originalUrl = shortLinkService.decode(shortUrl);
        return Map.of("originalUrl", originalUrl);
    }
}