package com.example.springproject.Controllers;

import com.example.springproject.Entity.User;
import com.example.springproject.Services.UserService;
import com.example.springproject.Services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Controller

public class RegisterController {
    public RegisterController(UtilityService utilityService, UserService userService) {
        this.utilityService = utilityService;
        this.userService = userService;
    }
    public RegisterController(){

    }

    @Autowired
    UtilityService utilityService;
    @Autowired
    UserService userService;
    @GetMapping("/registerPage")
    public String registerPage(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("error")!=null){
            model.addAttribute("error",request.getSession().getAttribute("error"));
            request.getSession().setAttribute("error",null);
        }
        utilityService.setStrings(model,request);
        return "Register.html";
    }
    @PostMapping("/registration")
    public String registerUser(Model model, User user, HttpServletRequest request){
    utilityService.setStrings(model,request);
    try{userService.registerUser(user);}
    catch (IllegalArgumentException e) {
        request.getSession().setAttribute("error",e.getMessage());
        return "redirect:/registerPage";
    }catch (ConstraintViolationException e){
        request.getSession().setAttribute("error","WrongFormat");
        return "redirect:/registerPage";
    }
    model.addAttribute("registered",true);
    return  "login.html";
}
}
