package ibm.java.academy.cerfiticationsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ibm.java.academy.cerfiticationsapp.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long>{
    
    List<User> findAllByNameOrderBySurname(String name);
}
