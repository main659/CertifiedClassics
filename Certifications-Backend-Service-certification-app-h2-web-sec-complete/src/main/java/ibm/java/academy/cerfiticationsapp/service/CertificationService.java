package ibm.java.academy.cerfiticationsapp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import ibm.java.academy.cerfiticationsapp.model.Certification;
import ibm.java.academy.cerfiticationsapp.model.Skill;
import ibm.java.academy.cerfiticationsapp.repository.CertificationJpaRepository;
import ibm.java.academy.cerfiticationsapp.repository.SkillJpaRepository;
import lombok.extern.java.Log;

@Service
@Validated
@Log
public class CertificationService {
    
    @Autowired
    private CertificationJpaRepository certRepo;

    @Autowired
    private SkillJpaRepository skillRepo;

    public BigDecimal getSumPrice() {
        List<Certification> allCerts = certRepo.findAll();
        BigDecimal sum = allCerts.stream().filter(x -> x.getPrice() != null).map(x -> x.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;
    }

    public Certification saveCertification(@Valid Certification certification) {
        certification = certRepo.save(certification);
        return certification;
    }

    @Transactional
    public Certification updateAndSave(Long certId, String name, List<Long> skillIds) {
        Certification certification = null;
        Optional<Certification> certOpt = certRepo.findById(certId);

        
		if(certOpt.isPresent()) {
			Certification cert = certOpt.get();
            if(StringUtils.hasText(name)) {
                cert.setName(name);
            }	
            if(!CollectionUtils.isEmpty(skillIds)) {
                List<Skill> foundSkills = skillRepo.findAllByIdIn(skillIds);
                if(!CollectionUtils.isEmpty(foundSkills)) {
                    cert.setSkills(foundSkills);
                }
            }
            	
			certification = saveCertification(cert);
            
			log.info("Certification modified: " + certification.toString());
		}
        return certification;
    }

    

    // public Certification assignSkills() {

    // }
}
