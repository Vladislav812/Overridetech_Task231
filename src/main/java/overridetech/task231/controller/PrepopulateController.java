package overridetech.task231.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import overridetech.task231.service.UserService;

@Controller
public class PrepopulateController {
    @Autowired
    UserService userService;

    @GetMapping("/prepop")
    public void prepopulateController() {
        userService.prepopulateDB();
    }

}