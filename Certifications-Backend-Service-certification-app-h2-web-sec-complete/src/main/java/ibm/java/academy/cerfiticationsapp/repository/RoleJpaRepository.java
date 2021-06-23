package ibm.java.academy.cerfiticationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ibm.java.academy.cerfiticationsapp.model.Role;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
}
