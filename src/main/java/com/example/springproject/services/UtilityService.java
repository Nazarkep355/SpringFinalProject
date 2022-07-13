package com.example.springproject.services;

import com.example.springproject.entity.User;
import com.example.springproject.repository.RouteRepository;
import com.example.springproject.repository.TrainRepository;
import com.example.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;
@Service
public class UtilityService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    TrainRepository trainRepository;
    public ResourceBundle getLocaleBundle(HttpServletRequest request){
        Locale locale= new Locale("locale_en");
        if(request.getSession().getAttribute("locale")!=null)
            locale= (Locale) request.getSession().getAttribute("locale");
        ResourceBundle bundle = ResourceBundle.getBundle(locale.getDisplayName());
        return bundle;
    }
    public void setStrings(Model model, HttpServletRequest request){
        ResourceBundle bundle = getLocaleBundle(request);
        for(String s :bundle.keySet()){
            model.addAttribute(s,bundle.getString(s));}
    }
    public void addUserInformation(Model model, HttpServletRequest request){
        Iterable<User> users =userRepository.findAll();
        if(request.getRemoteUser()!=null){
            for(User user : users)
                if(request.getRemoteUser().equals(user.getEmail())){
                    model.addAttribute("money",user.getMoney());
                    model.addAttribute("user",user.getName());
                    model.addAttribute("isAdmin",user.isAdmin());}
        }
    }
}

