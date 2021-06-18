package ibm.java.academy.cerfiticationsapp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTests {

    @LocalServerPort
	private int port;

    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
	private MockMvc mockMvc;

    @Test
    void helloTest() {
        Assertions.assertEquals(restTemplate.getForObject("http://localhost:" + port + "/hi?name=Mato"
            , String.class),"Ahoj Mato");
    }

    @Test
	public void helloWithMockMvnTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hi"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Ahoj Anonumous")));
	}

    @Test
    @WithMockUser(username = "admin", password = "admin")
	public void securityTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/all-users"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("emusk@tesla.com")));
	}
    
}
