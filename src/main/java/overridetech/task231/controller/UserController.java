package overridetech.task231.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.model.User;
import overridetech.task231.service.UserServiceImpl;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/newuser")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/newuser")
    public String returnStartPage(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllByOrderByIdAsc());
        return "start";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String editById(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
