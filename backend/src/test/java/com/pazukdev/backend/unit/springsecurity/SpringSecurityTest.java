package com.pazukdev.backend.unit.springsecurity;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Siarhei Sviarkaltsau
 */
//@RunWith(SpringRunner.class)
//@WebMvcTest(TestController.class)
public class SpringSecurityTest {

    //@Autowired
    private MockMvc mvc;

    //@WithMockUser(value = "spring")
    //@Test
    public void authorizedRequestToSecuredUrl() throws Exception {
        mvc
                .perform(get("/test/secured").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //@Test
    public void invalidPass() throws Exception {
        mvc
                .perform(formLogin().password("invalid"))
                .andExpect(unauthenticated());
    }

    //@Test
    public void anonymousUser() throws Exception {
        mvc
                .perform(get("/test/secured").with(anonymous()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

}
