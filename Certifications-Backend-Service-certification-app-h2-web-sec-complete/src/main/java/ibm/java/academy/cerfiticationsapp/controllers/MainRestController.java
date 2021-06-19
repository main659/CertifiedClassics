package ibm.java.academy.cerfiticationsapp.controllers;

import java.util.Arrays;
import java.util.List;

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
/*
    @GetMapping("/getUser/{email}")
    @ResponseBody
    public String findUserByEmail(@PathVariable(email = "email") String email){
        List<User> users = userRepo.findAll();
        for(User u : users){
            if(u.getEmail().equals(email)){
                return "Found user with email: " + email;
            }
        }

        return "User not found!!!";
    }
   */ 
}
