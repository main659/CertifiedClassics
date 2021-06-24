package ibm.java.academy.cerfiticationsapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;

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

import ibm.java.academy.cerfiticationsapp.model.Role;
import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.model.Voucher;
import ibm.java.academy.cerfiticationsapp.repository.RoleJpaRepository;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;
import ibm.java.academy.cerfiticationsapp.request.VoucherUpdateRequest;
import ibm.java.academy.cerfiticationsapp.response.MessageResponse;
import ibm.java.academy.cerfiticationsapp.service.SendEmailService;
import ibm.java.academy.cerfiticationsapp.service.VoucherService;

@RestController
public class MainRestController {
    @Autowired
    private VoucherService voucherService;

    @Autowired 
    UserJpaRepository userRepo;

    @Autowired 
    RoleJpaRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

      //email 
      @Autowired
      private SendEmailService sendEmailService;

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

    @GetMapping("/getuser")
    @ResponseBody
    public User users(HttpServletRequest request) {
        String token = request.getHeader("auth_token");
        try{
            String email = JWT.require(Algorithm.HMAC512("secret"))
                        .build()
                        .verify(token)
                        .getSubject();

                        User newUser = userRepo.findByEmail(email).orElse(new User());
                        System.out.println(newUser.getRoles());
                       return userRepo.findByEmail(email).orElse(new User());
            }catch(JWTDecodeException jde){
                return null;
            }
    }

    @PostMapping(path = "/add-user", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        System.out.println("Creating user: " + user.toString());
        User newUser = new User(user.getName(), user.getSurname(),
         user.getEmail(), passwordEncoder.encode(user.getPassword()));
        userRepo.save(newUser);
        Role basicRole = roleRepo.findById(7L).orElse(new Role("USER"));
        if(user.getEmail().equals("david.lumen@gmail.com")){
            Role adminRole = roleRepo.findById(5L).orElse(new Role("ADMIN"));
            newUser.getRoles().addAll(Arrays.asList(basicRole, adminRole));
        }else{
            newUser.getRoles().addAll(Arrays.asList(basicRole));
        }
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
    public String logoutUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";  //Where you go after logout here.
    }

    @GetMapping("/sendEmailToAll")
    public String sendEmailToAll(){
         
        List<User> users = userRepo.findAll();
        String s = "";
        for(User u : users){
            s += u.getEmail() + " ";
        }

        return "EMail sent to all users...";

    }

}
