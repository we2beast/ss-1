package com.poluhin.ss.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create resource")
    void successCreateResourceRoute() throws Exception {
        mockMvc.perform(post("/resource")
                        .content("{ \"id\": 1, \"value\": \"val1\", \"path\": \"path1\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("Get resource after create")
    void successGetResourceRoute() throws Exception {
        mockMvc.perform(post("/resource")
                        .content("{ \"id\": 1, \"value\": \"val1\", \"path\": \"path1\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        mockMvc.perform(get("/resource/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("Get not found resource")
    void notFoundGetResourceRoute() throws Exception {
        mockMvc.perform(get("/resource/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

}
