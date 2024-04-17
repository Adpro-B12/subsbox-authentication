package id.ac.ui.cs.advprog.authenticationuserprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class TestController {
    String createHTML = "userCreate";
    @GetMapping("")
    @ResponseBody
    public String createUserPage(Model model) {
        return "<h1>Welcome to Authentication & User Profile!</h1>";
    }
}