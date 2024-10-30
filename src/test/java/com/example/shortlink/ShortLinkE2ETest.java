package com.example.shortlink;

import com.example.shortlink.service.ShortLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortLinkE2ETest {

    @Autowired
    private MockMvc mockMvc;

    private ShortLinkService shortLinkService;

    @BeforeEach
    void setUp() {
        shortLinkService = Mockito.mock(ShortLinkService.class);
    }

    @Test
    void testEndToEnd() throws Exception {
        String originalUrl = "https://example.com/library/react";
        String shortUrl = "http://short.est/1";

        // Mock the encoding response
        Mockito.when(shortLinkService.encode(originalUrl)).thenReturn(shortUrl);
        Mockito.when(shortLinkService.decode(shortUrl)).thenReturn(originalUrl);

        // Perform the encode request
        mockMvc.perform(post("/api/encode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"" + originalUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value(shortUrl));

        // Perform the decode request
        mockMvc.perform(get("/api/decode")
                        .param("shortUrl", shortUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value(originalUrl));
    }
}