package ibm.java.academy.cerfiticationsapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.model.Voucher;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.repository.VoucherJpaRepository;

@Service
public class VoucherService {

    @Autowired
    private UserJpaRepository userRepo;

    @Autowired
    private VoucherJpaRepository voucherRepo;
    
    public Voucher assignVoucherToUser(Long voucherId, Long userId) {
        Voucher result = null;

        Optional<Voucher> voucherOpt = voucherRepo.findById(voucherId);
        Optional<User> userOpt = userRepo.findById(userId);

        if(voucherOpt.isPresent() && userOpt.isPresent()) {
            result = voucherOpt.get();
            result.setUser(userOpt.get());
            result = voucherRepo.save(result);
        }
        
        return result;
    }

    public void deleteVoucher(Long voiucherId) {
        voucherRepo.deleteById(voiucherId);
    }

}
