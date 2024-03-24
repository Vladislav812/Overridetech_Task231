package overridetech.task231.controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.dto.ProductDto;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.dto.UserDto;
import overridetech.task231.service.ProductService;
import overridetech.task231.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Secured("ROLE_admin")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/roles")
//    @Secured("ROLE_admin")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    @GetMapping("/users")
//    @Secured("ROLE_admin")
    public List<User> getAllUsers() {
        return userService.findAllByOrderByIdAsc();
    }

    @PostMapping(path = "/users", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
//    @Secured("ROLE_admin")
    public void createNewUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PatchMapping(path = "/users", consumes = "application/json")
//    @Secured("ROLE_admin")
    public void editUser(@RequestBody UserDto userDto) {
        userService.patchUser(userDto);
    }

    @DeleteMapping(path = "/users", consumes = "application/json")
//    @Secured("ROLE_admin")
    public void deleteUser(@RequestBody JSONObject userToDelete) {
        userService.deleteUser(userToDelete);
    }

    @GetMapping("/principal")
    @Secured({"ROLE_user", "ROLE_admin", "ROLE_seller"})
    public User getPrincipal(Authentication authentication) {
        return userService.findById(((User) authentication.getPrincipal()).getId());
    }

    @GetMapping("/postoffices")
    @Secured({"ROLE_user", "ROLE_admin", "ROLE_seller"})
    public JSONArray getNearestPostOffices(@RequestParam(name = "id") String userId) {
        Long id = Long.parseLong(userId);
        return userService.getNearestPostOffices(id);
    }


}
