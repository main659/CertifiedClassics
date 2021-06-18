package ibm.java.academy.cerfiticationsapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ibm.java.academy.cerfiticationsapp.model.Certification;
import ibm.java.academy.cerfiticationsapp.model.Certification.State;
import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.CertificationJpaRepository;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.CertificationService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CerfiticationsAppApplicationTests {

	@Test
	void contextLoads() {
	}

	//JPA
	@Test
	void findCertificationByIdJPATest(@Autowired CertificationJpaRepository certRepo) {
		Optional<Certification> certOpt = certRepo.findById(10000L);
		Assertions.assertTrue(certOpt.isPresent());
	    
		BigDecimal price = certOpt.get().getPrice();
		Assertions.assertNotNull(price);
		Assertions.assertEquals(0, price.compareTo(new BigDecimal("200.50")));
		
	}

	@Test
	void sumCertificationPricesTest(@Autowired CertificationService certService) {
		BigDecimal sum =  certService.getSumPrice();
		log.info("Sum of certifications: " + sum);
		Assertions.assertTrue(sum.compareTo(new BigDecimal("300")) > 0);
	}

	@Test
	@Transactional
	void sortedUsers(@Autowired UserJpaRepository userRepository) {
		List<User> sortedUserList = userRepository.findAllByNameOrderBySurname("Tom");
		Optional<User> firstUserOpt = sortedUserList.stream().findFirst();
		String usersurname = "";
		sortedUserList.stream().forEach(x -> log.info(x.toString()));
		if(firstUserOpt.isPresent()) {
			usersurname = firstUserOpt.get().getSurname();
		}
		Assertions.assertEquals(usersurname, "Black");
	}

	@Test
	@Disabled
	void saveCertificationLengthTest(@Autowired CertificationService certService) {
		Certification certification = new Certification(10004L, "My cert name is too long to save it to my great H2", "https://mycert.com", BigDecimal.valueOf(200), "EUR", State.ACTIVE);
		certService.saveCertification(certification);
		log.info("Created certification: " + certification.toString());
	}

	@Test
	void saveCertificationSeqTest1(@Autowired CertificationService certService, @Autowired UserJpaRepository userRepository) {
		Certification certification = new Certification("My seq test1", "https://mycert.com", BigDecimal.valueOf(200), "EUR", State.ACTIVE);
		Certification certification2 = new Certification("My seq test2", "https://mycert.com", BigDecimal.valueOf(200), "EUR", State.ACTIVE);
		User user = new User("Jozko", "Mrkvicka", "fajnamrkva@damaca.sk");
	    certService.saveCertification(certification);
		certService.saveCertification(certification2);
		userRepository.save(user);
		log.info("Created certification1: " + certification.toString());
		log.info("Created certification2: " + certification2.toString());
		log.info("Created User: " + user.toString());
	}

	@Test
	@Order(1)
	void saveCertificationAuditing(@Autowired CertificationService certService) {
		certService.updateAndSave(10001L, "Name updated", Arrays.asList(20001L, 20002L));
	}

	@Test
	@Transactional
	@Order(2)
	void findCertification(@Autowired CertificationJpaRepository certRepo) {
		Optional<Certification> certOpt = certRepo.findById(10001L);
		Assertions.assertTrue(certOpt.isPresent(),"Certification with id=" + 10001L + " not found!");
		if(certOpt.isPresent()) {
			Certification cert = certOpt.get();
			log.info("Certification after auditing: " + cert.toString());
			Assertions.assertEquals(1, cert.getVersion());
		}
		
	}

	@Test
	@Transactional
	void testJson(@Autowired CertificationJpaRepository certRepo, @Autowired UserJpaRepository userRepo) throws JsonProcessingException{
		Certification certification = certRepo.findById(10001L).get();

		String resultCert = new ObjectMapper().writeValueAsString(certification);
		log.info("-----Certification Json: " + resultCert);

		User user = userRepo.findById(10012L).get();

		String resultUser = new ObjectMapper().writeValueAsString(user);
		log.info("-----User Json: " + resultUser);
	}

}
