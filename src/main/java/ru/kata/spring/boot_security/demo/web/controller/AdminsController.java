package ru.kata.spring.boot_security.demo.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminsController {
    @GetMapping("/admin")
    public String adminPage() {
    return "admin/adminPanel";
}

    @GetMapping("/user")
    public String userPage() {
        return "user/user";
    }

    @GetMapping("/")
    public String loginPage() {
//        System.out.println("pppppppppppppppppppppppppppppppppppppppppppppppp");
        return "all/hello";

    }
}