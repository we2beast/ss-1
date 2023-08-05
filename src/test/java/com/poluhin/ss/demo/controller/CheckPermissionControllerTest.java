package com.poluhin.ss.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckPermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Check if permission exists for user route")
    @WithMockUser(username = "user", roles = {"USER"})
    void successUserRoute() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("user"))
                .andExpect(authenticated().withRoles("USER"))
                .andExpect(content().string("User permission"));
    }

    @Test
    @DisplayName("Check if permission does not exists for admin route")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void failedUserRoute() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Check if permission exists for admin route")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void successAdminRoute() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("admin"))
                .andExpect(authenticated().withRoles("ADMIN"))
                .andExpect(content().string("Admin permission"));
    }

    @Test
    @DisplayName("Check if permission does not exists for user route")
    @WithMockUser(username = "user", roles = {"USER"})
    void failedAdminRoute() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
