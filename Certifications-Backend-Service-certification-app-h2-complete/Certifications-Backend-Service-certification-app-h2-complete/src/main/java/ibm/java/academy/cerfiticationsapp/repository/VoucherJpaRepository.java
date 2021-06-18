package ibm.java.academy.cerfiticationsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ibm.java.academy.cerfiticationsapp.model.Voucher;

public interface VoucherJpaRepository extends JpaRepository<Voucher, Long>{
    
}
