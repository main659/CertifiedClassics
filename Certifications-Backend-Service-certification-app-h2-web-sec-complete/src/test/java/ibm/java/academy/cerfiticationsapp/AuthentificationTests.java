package ibm.java.academy.cerfiticationsapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import lombok.extern.java.Log;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Log
public class AuthentificationTests {

    @LocalServerPort
	private int port;

    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired 
    UserJpaRepository userRepo;

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @Order(1)
    void addUserTest() throws URISyntaxException{
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+ port + "/add-user";
        URI uri = new URI(baseUrl);
        User user = new User("David", "Ocepek", "ibm.example@gmail.com", "HelloWorld");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");  

        HttpEntity<User> req = new HttpEntity<>(user, headers);
        ResponseEntity<String> res = restTemplate.postForEntity(uri, req, String.class);

        Assertions.assertEquals(200, res.getStatusCodeValue());

        Optional<User> userOpt = userRepo.findByName("David");
        log.info("This is the user: " + userOpt.toString());
		Assertions.assertTrue(userOpt.isPresent());
    }

    //Fail Authentificating user
    @Test
    @Order(2)
    void unauthorizedUserTest() throws URISyntaxException{
        final String baseUrl = "http://localhost:"+ port + "/all-users";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");  

        ResponseEntity<String> res = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(401, res.getStatusCodeValue());
    }

    //Authenticate User
    @Test
    @Transactional
    @Order(3)
    @Disabled
    void authenticateUserTest() throws URISyntaxException{
        final String loginUrl = "http://localhost:"+ port + "/login";
        URI uri = new URI(loginUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); 
        headers.setBasicAuth("username", "password");

        Map<String, String> map = new HashMap<>();
        map.put("username", "David");
        map.put("password", "HelloWorld");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> resLogin = restTemplate.postForEntity(loginUrl, entity, String.class);

        Assertions.assertEquals(200, resLogin.getStatusCodeValue());
    }

    void getAllUsersTest() throws URISyntaxException{}

    //Check for new JWT token
}
