package ske.aurora.openshift.referanse.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import ske.aurora.openshift.referanse.test.ApplicationConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void liveliness() throws Exception {
        // TODO: Assert 200, not 404
        mockMvc.perform(get(ApplicationConstants.PING_URL))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void contextLoads() {
    }

}
