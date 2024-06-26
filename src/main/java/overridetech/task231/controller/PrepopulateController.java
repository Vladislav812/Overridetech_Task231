package overridetech.task231.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import overridetech.task231.service.UserService;

@RestController
public class PrepopulateController {
    @Autowired
    UserService userService;

    @GetMapping("/prepop")
    public String prepopulateController() {
        if (userService.findAllByOrderByIdAsc().isEmpty()) {
            userService.prepopulateDB();
            return "Prepopulation performed!";
        } else {
            return "Prepopulation has been already done! (Database is not empty!)";
        }
    }

}
