package ibm.java.academy.cerfiticationsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibm.java.academy.cerfiticationsapp.repository.UserJpaRepository;

@Controller
@RequestMapping("view")
public class MainViewController {

    @Autowired
    UserJpaRepository userRepo;

    @GetMapping
    public String indexView(@RequestParam(name = "name", required = false) String name, Model model) {
        if (name != null) {
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "Anonymous");
        }
        return "greeting";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "listAll";
    }
}
