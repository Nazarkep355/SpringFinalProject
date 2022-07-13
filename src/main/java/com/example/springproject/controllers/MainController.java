package com.example.springproject.controllers;

import com.example.springproject.services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class MainController {
    public MainController() {
    }

    public MainController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @Autowired
    private UtilityService utilityService;

    @GetMapping("/home")
    public String HomePage(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("error") != null) {
            model.addAttribute("error", request.getSession().getAttribute("error"));
            request.getSession().setAttribute("error", null);
        }
        utilityService.setStrings(model, request);
        utilityService.addUserInformation(model, request);
        return "home.html";
    }

    @GetMapping("/")
    public RedirectView redirectOnHome() {
        return new RedirectView("/home");
    }

    @GetMapping("/login")
    public String LoginPage(Model model, HttpServletRequest request) {
//        ResourceBundle bundle = utilityService.getLocaleBundle(request);
        utilityService.setStrings(model, request);
//        for(String s :bundle.keySet()){
//            if(s.equals("Register")){model.addAttribute(s,bundle.getString(s).toLowerCase());}else
//                model.addAttribute(s,bundle.getString(s));}
        return "login.html";
    }

    @GetMapping("/locale_en")
    public String changeLocaleToEn(HttpServletRequest request) {
        String redirecting = request.getHeader("referer").split("8080/")[1];
        request.getSession().setAttribute("locale", new Locale("locale_en"));
        if (request.getParameter("page") == null) {
            return "redirect:/" + redirecting;
        } else return "redirect:/" + request.getParameter("page");
    }

    @GetMapping("/locale_ua")
    public String changeLocaleToUa(HttpServletRequest request) {
        String redirecting = request.getHeader("referer").split("8080/")[1];
        request.getSession().setAttribute("locale", new Locale("locale_ua"));
        if (request.getParameter("page") == null) {
            return "redirect:/" + redirecting;
        } else return "redirect:/" + request.getParameter("page");
    }


}