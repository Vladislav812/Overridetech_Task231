package overridetech.task231.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.repository.UserRepository;
import overridetech.task231.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @PostMapping("admin/newuser")
    public String addNewUser(@Valid @ModelAttribute("user") User user, Errors errors, Model model)
//            , @RequestParam(value = "roleslist", required = false)
//                             Long[] roleslist)
    {
        if (!userRepository.findByName(user.getName()).isEmpty()) {
            errors.rejectValue("name", "", "Name cannot be present in database!");
        }
//        if (roleslist == null || roleslist.length < 1) {
//            errors.rejectValue("currentRoles", "", "There must be as least on role!");
//        }


        if (user.getCurrentRoles() == null || user.getCurrentRoles().isEmpty()) {
            errors.rejectValue("currentRoles", "", "There must be at least one role!");
        }
        if (errors.hasErrors()) {
            Set<Role> roleSet = roleRepository.findAll();
            user.setCurrentRoles(roleSet);
            return "new";
        }
        Set<Role> newroles = user.getCurrentRoles();
        newroles.forEach(System.out::println);
        System.out.println();
//        Set<Role> rolesFound = roleRepository.findByIdIn(Arrays.asList(roleslist));
//        user.setCurrentRoles(rolesFound);
//        userService.saveUser(user);
//        rolesFound.forEach(System.out::println);
        userRepository.save(user);


//        Role defaultRole = roleRepository.findRoleByTitle("ROLE_user");
//        Set<Role> roles = new HashSet<>();
//        roles.add(defaultRole);

//        Long generatedUserId = userRepository.findUserByName(user.getName()).getId();
//
//        User updatedUser = userRepository.findById(generatedUserId).get();
//        updatedUser.setCurrentRoles(roles);
//        userRepository.save(updatedUser);

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/newuser")
    public String returnStartPage(Model model) {
        Set<Role> roleSet = roleRepository.findAll();
//
//        User user = new User();
//        user.setCurrentRoles(roleSet);
        model.addAttribute("user", new User());

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
        System.out.println(userService.findById(id).getRolesId());
        return "edit";
    }


    @PatchMapping("/admin/edit/{id}")
    public String editById(@Valid @ModelAttribute("user") User user, Errors errors, @PathVariable("id") long id) {
        List<User> list = userRepository.findByName(user.getName());
        if (!list.isEmpty() && list.stream().anyMatch(usr -> usr.getId() != id)) {
            errors.rejectValue("name", "", "Name cannot be present in database!");
        }

        if (user.getCurrentRoles() == null || user.getCurrentRoles().isEmpty()) {
            errors.rejectValue("currentRoles", "", "There must be at least one role!");
        }

        if (errors.hasErrors()) {
            return "edit";
        }
//
//        Set<Role> roles = userRepository.findById(id).get().getCurrentRoles();
//        user.setCurrentRoles(roles);
//        userRepository.save(user);


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
