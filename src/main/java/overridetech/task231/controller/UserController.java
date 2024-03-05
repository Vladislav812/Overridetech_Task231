package overridetech.task231.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/start")
    public String returnStartPage() {
        return "start";
    }

    @GetMapping("/userpage")
    public String returnUserPage() {
        return "userpage";
    }

}
