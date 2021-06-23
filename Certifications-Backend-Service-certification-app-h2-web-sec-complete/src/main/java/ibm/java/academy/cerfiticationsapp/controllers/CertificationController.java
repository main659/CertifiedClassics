package ibm.java.academy.cerfiticationsapp.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibm.java.academy.cerfiticationsapp.model.Certification;
import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.CertificationJpaRepository;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.service.CertificationService;
import ibm.java.academy.cerfiticationsapp.service.SendEmailService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("certs")
class CertificationController {

    @Autowired
    CertificationJpaRepository certificationJpaRepository;

    @Autowired
    CertificationService certService;

    


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/proposal", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestBody Certification certificationRequest) {
        try {
            logger.info(certificationRequest.toString());
            Certification cert = certService.saveCertification(certificationRequest);
            return new ResponseEntity<>(cert, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Certification certification, @PathVariable Long id) {
        return certificationJpaRepository.findById(id).map(cert -> {
            cert.setName(certification.getName());
            cert.setPrice(certification.getPrice());
            cert = certificationJpaRepository.save(cert);
            return new ResponseEntity<>(cert, HttpStatus.OK);
        }).orElseGet(() -> {
            Certification cert = certificationJpaRepository.save(certification);
            return new ResponseEntity<>(cert, HttpStatus.CREATED);
        });
    }

    @GetMapping
    public Certification getCertification() {
        Optional<Certification> certOpt = certificationJpaRepository.findById(10001L);
        Certification cert = certOpt.get();
        return cert;
    }
}
