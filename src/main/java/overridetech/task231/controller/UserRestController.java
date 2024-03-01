package overridetech.task231.controller;

import netscape.javascript.JSObject;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import overridetech.task231.model.Role;
import overridetech.task231.model.User;
import overridetech.task231.repository.RoleRepository;
import overridetech.task231.repository.UserRepository;
import overridetech.task231.service.UserService;

import java.util.*;

@RestController
public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sayhello")
    @Cacheable
    public List<User> sayHello() {
        System.out.println("returning all users");
        return userService.findAllByOrderByIdAsc();
    }

//    @PostMapping("/testjs")
//    public ResponseEntity testMethod(@RequestBody String s) {
//        System.out.println("printing string");
//        System.out.println(s);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @PostMapping(path = "/createuser", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<User> postRestUser(@RequestBody User user) {
        System.out.println("creating: ");
        System.out.println(user.getCurrentRoles());
            userService.saveUser(user);

////        System.out.println(payload);
////        System.out.println(payload.get("currentRoles"));
//        User user = new User();
//        user.setName(payload.get("name").toString());
//        user.setEmail((String) payload.get("email"));
//        user.setPassword((String) payload.get("password"));
//        user.setAge(Integer.parseInt((String) payload.get("age")));
//
//        String[] roles = (payload.get("currentRoles")).toString().split(",");
//        System.out.println(Arrays.toString(roles));
////        inboundRoles.forEach(System.out::println);
////        Set<Role> roleSet = new HashSet<>();
////        for (String role : inboundRoles) {
////            roleSet.add(roleRepository.findRoleByTitle("ROLE_" + role));
////        }
////        System.out.println(roleSet);
////        user.setCurrentRoles(roleSet);
//////        System.out.println(roles);
////        userService.saveUser(user);
////        return sayHello();
        return userService.findAllByOrderByIdAsc();
    }

    @GetMapping("/getroles")
    public List<Role> returnAllRolesList() {
        System.out.println("returning roles");
        return new ArrayList<Role>(roleRepository.findAll());
    }





    @DeleteMapping("/deleteuser")
    public void deleteUser(@RequestBody String name) {
//        System.out.println(name);
//        User user = userRepository.findUserByName(name);
//        userRepository.
        userRepository.deleteByName(name);
//        userRepository.delete(user);
    }

    @GetMapping("/principal")
    public User getPrincipal(Authentication authentication){
        return (User) authentication.getPrincipal();
    }

}
