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
    public String addNewUser(@Valid @ModelAttribute("user") User user, Errors errors) {
        if (!userRepository.findByName(user.getName()).isEmpty()) {
            errors.rejectValue("name", "", "Name cannot be present in database!");
        }
        if (errors.hasErrors()) {
            return "new";
        }
        userService.saveUser(user);

        Role defaultRole = roleRepository.findRoleByTitle("ROLE_user");
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        Long generatedUserId = userRepository.findUserByName(user.getName()).getId();

        User updatedUser = userRepository.findById(generatedUserId).get();
        updatedUser.setCurrentRoles(roles);
        userRepository.save(updatedUser);

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/newuser")
    public String returnStartPage(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAllByOrderByIdAsc());
        return "usersummary";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findById(id));
        System.out.println("edit");
        return "edit";
    }

//    @GetMapping("/user/{id}")
//    public String getUser(Model model, @PathVariable("id") long id) {
//        model.addAttribute("user", userService.findById(id));
//        return "userId";
//    }

    @GetMapping("/user")
    public String returnCurrentUser(Model model, Authentication authentication) {
        model.addAttribute("user", (User) authentication.getPrincipal());
        return "userId";
    }

    @PatchMapping("/admin/edit/{id}")
    public String editById(@Valid @ModelAttribute("user") User user, Errors errors, @PathVariable("id") long id) {
        System.out.println("patch");
        List<User> list = userRepository.findByName(user.getName());
        if (!list.isEmpty() && list.stream().anyMatch(usr -> usr.getId() != id)) {
            errors.rejectValue("name", "", "Name cannot be present in database!");
        }
        if (errors.hasErrors()) {
            return "edit";
        }

        Set<Role> roles = userRepository.findById(id).get().getCurrentRoles();
        user.setCurrentRoles(roles);
        userRepository.save(user);


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

    @GetMapping("/principal")
    @ResponseBody
    public String returnPrincipal(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName() + ",  " + user.getCurrentRoles().toString());
        return sb.toString();
    }
}
