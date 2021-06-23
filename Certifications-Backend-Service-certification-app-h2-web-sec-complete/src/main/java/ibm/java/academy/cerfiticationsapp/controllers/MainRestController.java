package ibm.java.academy.cerfiticationsapp.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.model.Voucher;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.request.VoucherUpdateRequest;
import ibm.java.academy.cerfiticationsapp.response.MessageResponse;
import ibm.java.academy.cerfiticationsapp.service.VoucherService;

@RestController
public class MainRestController {
    @Autowired
    private VoucherService voucherService;

    @Autowired 
    UserJpaRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(path = "/hello/{name}/*/{age}")
    @ResponseBody
    public String helloPathVariable(@PathVariable(name = "name") String name, @PathVariable(name = "age", required = false) Integer age) {
        return "Cau  " + name + ". Age: " + age;
    }

    @GetMapping("/all-users")
    @ResponseBody
    public List<User> users() {
        return userRepo.findAll();
    }

    @PostMapping(path = "/add-user", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        System.out.println("Creating user: " + user.toString());
        User newUser = new User(user.getName(), user.getSurname(),
         user.getEmail(), passwordEncoder.encode(user.getPassword()));
        return userRepo.save(newUser);
    }

    @DeleteMapping("/delete-user")
    @ResponseBody
    public void deleteUser(@RequestParam("id") Long id) {
        userRepo.deleteAllById(Arrays.asList(id));
    }

    @PostMapping("/login")
    public String showUser(){
        return "Hello World";
    }
    @RequestMapping(path = "/vouchers/update/" , method = RequestMethod.POST)
	public ResponseEntity<?> udpateVoucher(@Valid @RequestBody VoucherUpdateRequest updateRequest) {
		Voucher voucher = voucherService.assignVoucherToUser(updateRequest.getVoucherId(), updateRequest.getUserId());
		if(voucher == null){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Something is wrong!"));
		}
		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}

    @PostMapping("/logout")
    @ResponseBody
    public void logoutUser(){}
}
