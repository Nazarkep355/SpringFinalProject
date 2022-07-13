package com.example.springproject.controllers;

import com.example.springproject.entity.Train;
import com.example.springproject.entity.User;
import com.example.springproject.services.TrainService;
import com.example.springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    @Autowired
    UserService userService;
    @Autowired
    TrainService trainService;

    @GetMapping(value = "/userInfo", produces = "application/json")
    public User getUserInfo(HttpServletRequest request) {
        return userService.getUserByEmail(request.getRemoteUser());
    }

    @GetMapping(value = "/userInfoXml", produces = "application/xml")
    public User getUserXml(HttpServletRequest request) {
        return userService.getUserByEmail(request.getRemoteUser());
    }

    @GetMapping(value = "/trainInfo/{id}", produces = "application/json")
    public Train getUserInfo(@PathVariable("id") Long id) {
        return trainService.getTrainById(id);
    }

    @GetMapping(value = "/trainInfoXml/{id}", produces = "application/xml")
    public Train getUserInfoXml(@PathVariable("id") Long id) {
        return trainService.getTrainById(id);
    }

}
