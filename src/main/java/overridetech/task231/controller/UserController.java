package overridetech.task231.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.repository.UserRepository;
import overridetech.task231.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @PostMapping("admin/newuser")
    public String addNewUser(@Valid @ModelAttribute User user, Errors errors, Model model) {
        if (!userRepository.findByName(user.getName()).isEmpty()) {
            errors.rejectValue("name", "", "Name cannot be present in database!");
        }
        if (user.getCurrentRoles() == null || user.getCurrentRoles().isEmpty()) {
            errors.rejectValue("currentRoles", "", "There must be at least one role!");
        }
        if (errors.hasErrors()) {
            Set<Role> roleSet = roleRepository.findAll();
            model.addAttribute("rolesSet", roleSet);
            return "new";
        }
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/newuser")
    public String returnStartPage(Model model) {
        Set<Role> roleSet = roleRepository.findAll();

        Role defaultRole = roleRepository.findRoleByTitle("ROLE_user");
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        User user = new User();
        user.setCurrentRoles(roles);

        model.addAttribute("user", user);
        model.addAttribute("rolesSet", roleSet);

        return "new";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllByOrderByIdAsc());
        return "usersummary";
    }


    @GetMapping("/user")
    public String returnCurrentUser(Model model, Authentication authentication) {
        model.addAttribute("user", (User) authentication.getPrincipal());
        return "userId";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        Set<Role> allRoles = roleRepository.findAll();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("allRoles", allRoles);
        return "edit";
    }


    @PatchMapping("/admin/edit/{id}")
    public String editById(@Valid @ModelAttribute("user") User user, Errors errors, @PathVariable("id") long id, Model model) {
        List<User> list = userRepository.findByName(user.getName());
        if (!list.isEmpty() && list.stream().anyMatch(usr -> usr.getId() != id)) {
            errors.rejectValue("name", "", "Name cannot be present in database!");
        }
        if (user.getCurrentRoles() == null || user.getCurrentRoles().isEmpty()) {
            errors.rejectValue("currentRoles", "", "There must be at least one role!");
        }
        if (errors.hasErrors()) {
            Set<Role> allroles = roleRepository.findAll();
            model.addAttribute("allRoles", allroles);
            return "edit";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/start")
    public String returnBlankPage() {
        return "start";
    }
}
