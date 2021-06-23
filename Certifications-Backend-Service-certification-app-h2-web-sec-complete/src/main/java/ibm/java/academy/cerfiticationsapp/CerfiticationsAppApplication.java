package ibm.java.academy.cerfiticationsapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ibm.java.academy.cerfiticationsapp.model.Skill;
import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.SkillJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.CertificationService;
import ibm.java.academy.cerfiticationsapp.service.VoucherService;
import lombok.extern.java.Log;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableSwagger2
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
@Log
public class CerfiticationsAppApplication extends SpringBootServletInitializer implements WebMvcConfigurer, RepositoryRestConfigurer  {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CerfiticationsAppApplication.class, args);

		//voucher association for user
		//log.info(ctx.getBean(VoucherService.class).assignVoucherToUser(10020L, 10011L).toString());

		//Assign skill to certification
		//ctx.getBean(CertificationService.class).updateAndSave(10004L, null, Arrays.asList(20003L));

		RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/add-user";
        URI uri;
		try {
			uri = new URI(baseUrl);
			User user = new User("root", "root", "root", "root");

        	HttpHeaders headers = new HttpHeaders();
        	headers.set("Content-Type", "application/json");  

        	HttpEntity<User> req = new HttpEntity<>(user, headers);
        	ResponseEntity<String> res = restTemplate.postForEntity(uri, req, String.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Autowired
	private EntityManager entityManager;

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(e -> e.getJavaType()).collect(Collectors.toList()).toArray(new Class[0]));
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}
