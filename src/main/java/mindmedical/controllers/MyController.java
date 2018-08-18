package mindmedical.controllers;

import com.naturalprogrammer.spring.lemon.LemonController;
import mindmedical.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/core")
public class MyController extends LemonController<User, Long> {

}