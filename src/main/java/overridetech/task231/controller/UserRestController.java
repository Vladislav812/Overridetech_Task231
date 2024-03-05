package overridetech.task231.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    @Cacheable
    public List<User> sayHello() {
        return userService.findAllByOrderByIdAsc();
    }

    @PostMapping(path = "/users", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User postRestUser(@RequestBody User user) {
        Set<Role> currentRoles = new HashSet<>();
        for (Role role : user.getCurrentRoles()) {
            Long roleId = role.getId();
            currentRoles.add(roleRepository.findById(roleId).get());
        }
        user.setCurrentRoles(currentRoles);
        userService.saveUser(user);
        return user;
    }

    @PatchMapping(path = "/users", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User patchRestUser(@RequestBody User user) {
        Set<Role> currentRoles = new HashSet<>();
        for (Role role : user.getCurrentRoles()) {
            Long roleId = role.getId();
            currentRoles.add(roleRepository.findById(roleId).get());
        }
        userService.saveUser(user);
        return userService.findById(user.getId());
    }

    @GetMapping("/roles")
    public List<Role> returnAllRolesList() {
        return new ArrayList<Role>(roleRepository.findAll());
    }


    @DeleteMapping(path = "/users", consumes = "application/json")
    public void deleteUser(@RequestBody JSONObject del) {
        Long id = Long.parseLong((String) del.get("id"));
        userService.deleteById(id);
    }

    @GetMapping("/principal")
    public User getPrincipal(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

}
