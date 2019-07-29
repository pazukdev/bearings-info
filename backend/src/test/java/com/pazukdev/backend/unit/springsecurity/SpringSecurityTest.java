package com.pazukdev.backend.unit.springsecurity;

import com.pazukdev.backend.controller.DefaultController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DefaultController.class)
public class SpringSecurityTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(value = "spring")
    @Test
    public void authorizedRequestToSecuredUrl() throws Exception {
        mvc
                .perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void invalidPass() throws Exception {
        mvc
                .perform(formLogin().password("invalid"))
                .andExpect(unauthenticated());
    }

}
