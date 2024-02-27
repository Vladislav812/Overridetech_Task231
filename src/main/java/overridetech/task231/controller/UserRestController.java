package overridetech.task231.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import overridetech.task231.model.User;
import overridetech.task231.service.UserService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("hello")
    public List<User> getUsers() {
        List<User> users = userService.findAllByOrderByIdAsc();
        return users;
    }

    @PostMapping(path = "/testjs")
    public void testMethod(@RequestBody String s) {
        System.out.println("printing string");
        System.out.println(s);
    }
}
