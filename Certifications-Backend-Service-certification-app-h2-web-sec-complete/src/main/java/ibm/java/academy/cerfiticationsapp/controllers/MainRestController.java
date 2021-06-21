package ibm.java.academy.cerfiticationsapp.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;

@Controller
public class MainRestController {
    
    @Autowired 
    UserJpaRepository userRepo;

    @GetMapping("hi")
    @ResponseBody
    public String hello(@RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "lang", required = false) String lang) {
        if (lang != null && lang.equalsIgnoreCase("en")) {
            return "Hi " + (name == null ? "Anonumous" : name);
        } else {
            return "Ahoj " + (name == null ? "Anonumous" : name);
        }
    }

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

    @GetMapping(path = "/promoteManager/")
    @ResponseBody
    public String promoteManager(@RequestParam(name = "userEmail", required=true) String userEmail){
        /*Optional<User> admin = userRepo.findByEmail(adminEmail);
        if(admin!=null && admin.get().isAdmin()){
            Optional<User> user = userRepo.findByEmail(userEmail);
            user.get().setManager(true);
            userRepo.save(user.get());
            return "DONE";
        }*/
        Optional<User> user = userRepo.findByEmail(userEmail);
            user.get().setManager(true);
            userRepo.save(user.get());
            return "DONE";

       

    }

    @PostMapping(path = "/add-user", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @DeleteMapping("/delete-user")
    @ResponseBody
    public void deleteUser(@RequestParam("id") Long id) {
        userRepo.deleteAllById(Arrays.asList(id));
    }

    @DeleteMapping("/deleteUserByEmail/")
    @ResponseBody
    public String deleteUserByEmail(@RequestParam("userEmail") String email, @RequestParam("adminEmail") String adminMail){
        /*Optional<User> admin = userRepo.findByEmail(adminMail);
        if(admin!=null && admin.get().isAdmin()){
            Optional<User> user = userRepo.findByEmail(email);
            userRepo.delete(user.get());
        }*/
        Optional<User> user = userRepo.findByEmail(email);
            userRepo.delete(user.get());
        return "DONE";
    }

}
