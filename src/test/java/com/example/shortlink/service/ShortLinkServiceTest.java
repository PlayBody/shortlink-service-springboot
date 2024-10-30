package com.example.shortlink.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortLinkServiceTest {
    private ShortLinkService shortLinkService;

    @BeforeEach
    void setUp() {
        shortLinkService = new ShortLinkService();
    }

    @Test
    void testEncode() {
        String originalUrl = "https://example.com/library/react";
        String shortUrl = shortLinkService.encode(originalUrl);
        assertNotNull(shortUrl);
        assertTrue(shortUrl.startsWith("http://short.est/"));
    }

    @Test
    void testDecode() {
        String originalUrl = "https://example.com/library/react";
        String shortUrl = shortLinkService.encode(originalUrl);
        String decodedUrl = shortLinkService.decode(shortUrl);
        assertEquals(originalUrl, decodedUrl);
    }

    @Test
    void testDecodeNonExistentUrl() {
        String nonExistentShortUrl = "http://short.est/invalid";
        String decodedUrl = shortLinkService.decode(nonExistentShortUrl);
        assertEquals("URL not found", decodedUrl);
    }
}