package ske.aurora.openshift.referanse.springboot;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MainTest {

    private MockMvc mockMvc;

    //    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
//        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void liveliness() throws Exception {
//        mockMvc.perform(get(ApplicationConstants.PING_URL))
//            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void contextLoads() {
    }

}
