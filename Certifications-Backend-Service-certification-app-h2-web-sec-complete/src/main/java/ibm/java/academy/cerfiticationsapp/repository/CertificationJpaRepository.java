package ibm.java.academy.cerfiticationsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ibm.java.academy.cerfiticationsapp.model.Certification;

public interface CertificationJpaRepository extends JpaRepository<Certification, Long>{

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    public List<Certification> findByPriceLessThan(@Param("price") Double price);
    
}
