package overridetech.task231.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {


    @PostMapping(path = "/testjs")
    public void testMethod(@RequestBody String s) {
        System.out.println("printing string");
        System.out.println(s);
    }
}
