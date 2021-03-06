package ibm.java.academy.cerfiticationsapp.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import ibm.java.academy.cerfiticationsapp.model.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserJpaRepository extends JpaRepository<User, Long>{

    List<User> findAllByNameOrderBySurname(String name);
    @RestResource(path = "email", rel = "email")
    public Optional<User> findByEmail(@Param("email") String email);

    @RestResource(path = "name", rel = "name")
    public Optional<User> findByName(@Param("name") String username); 
}
