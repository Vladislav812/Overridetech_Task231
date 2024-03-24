package overridetech.task231.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Secured("ROLE_seller")
public class ProductController {

    @GetMapping("/productpage")
    public String getSingleProductPage() {
        return "productcard";
    }

//    @GetMapping("/productpage")
//    public String getSingleProductPage() {
//        return "productcard";
//    }
}
