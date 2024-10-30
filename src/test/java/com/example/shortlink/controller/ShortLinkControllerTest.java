package com.example.shortlink.controller;

import com.example.shortlink.service.ShortLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShortLinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortLinkService shortLinkService;

    @Test
    void testEncode() throws Exception {
        String originalUrl = "https://example.com/library/react";
        String shortUrl = "http://short.est/1";

        when(shortLinkService.encode(originalUrl)).thenReturn(shortUrl);

        mockMvc.perform(post("/api/encode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"" + originalUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value(shortUrl));
    }

    @Test
    void testDecode() throws Exception {
        String shortUrl = "http://short.est/1";
        String originalUrl = "https://example.com/library/react";

        when(shortLinkService.decode(shortUrl)).thenReturn(originalUrl);

        mockMvc.perform(get("/api/decode")
                        .param("shortUrl", shortUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value(originalUrl));
    }

    @Test
    void testDecodeNonExistentUrl() throws Exception {
        String shortUrl = "http://short.est/invalid";

        when(shortLinkService.decode(shortUrl)).thenReturn("URL not found");

        mockMvc.perform(get("/api/decode")
                        .param("shortUrl", shortUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value("URL not found"));
    }
}