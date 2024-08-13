package likelion.edu.vn.health_care.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {


    @GetMapping
    public String demo() {
        return "Hello World";
    }
}
