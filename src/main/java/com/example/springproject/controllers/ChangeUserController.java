package com.example.springproject.controllers;

import com.example.springproject.entity.User;
import com.example.springproject.services.UserService;
import com.example.springproject.services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class ChangeUserController {
    @Autowired
    UserService userService;
    @Autowired
    UtilityService utilityService;

    @GetMapping("/changeBalancePage")
    public String changeBalancePage(HttpServletRequest request, Model model) {
        utilityService.setStrings(model, request);
        utilityService.addUserInformation(model, request);
        return "ChangeMoney.html";
    }

    @PostMapping("/changeBalance")
    public String changeBalance(HttpServletRequest request, int money) {
        User user = userService.getUserByEmail(request.getRemoteUser());
        userService.changeBalance(user, money);
        return "redirect:/home";
    }
}
