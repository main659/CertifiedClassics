package ibm.java.academy.cerfiticationsapp.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ibm.java.academy.cerfiticationsapp.model.User;
import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;

@Controller
public class MainRestController {
    
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

    @PostMapping("/logout")
    @ResponseBody
    public void logoutUser(){}
}
