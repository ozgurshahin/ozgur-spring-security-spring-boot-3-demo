package org.example.ozgurspringsecurityspringboot3demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc//it is creating a mock of the web context
class DemoControllerTest {
    @Autowired
    private MockMvc api;

    @Test
    void anyoneCanViewPublicEndpoint() throws Exception {
        api.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("Hello")));
    }

    @Test
    void notLoggedInUserCannotViewSecuredEndpoint() throws Exception {
        api.perform(get("/secured"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void loggedInUserCanViewSecuredEndpoint() throws Exception {
        api.perform(get("/secured"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("Secured")));
    }

    @Test
    void notLoggedInUserCannotViewAdminEndpoint() throws Exception {
        api.perform(get("/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void simpleUserShouldNotSeeAdminEndpoint() throws Exception {
        api.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void adminUserCanViewAdminEndpoint() throws Exception {
        api.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("Admin")));
    }
}
