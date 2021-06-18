package ibm.java.academy.cerfiticationsapp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ibm.java.academy.cerfiticationsapp.model.Skill;
import ibm.java.academy.cerfiticationsapp.repository.SkillJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.CertificationService;
import ibm.java.academy.cerfiticationsapp.service.VoucherService;
import lombok.extern.java.Log;

@SpringBootApplication
//@Log
public class CerfiticationsAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CerfiticationsAppApplication.class, args);

		//voucher association for user
		//log.info(ctx.getBean(VoucherService.class).assignVoucherToUser(10020L, 10011L).toString());

		//Assign skill to certification
		//ctx.getBean(CertificationService.class).updateAndSave(10004L, null, Arrays.asList(20003L));
	}

}
