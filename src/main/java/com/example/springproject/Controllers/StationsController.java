package com.example.springproject.Controllers;

import com.example.springproject.Entity.Station;
import com.example.springproject.Services.StationService;
import com.example.springproject.Services.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/stations")
public class StationsController {
    @Autowired
    UtilityService utilityService;
    @Autowired
    StationService stationService;

    public StationsController(UtilityService utilityService, StationService stationService) {
        this.utilityService = utilityService;
        this.stationService = stationService;
    }

    @GetMapping("/all")
    public String stationList(Model model, HttpServletRequest request, int page){
        utilityService.addUserInformation(model, request);
        utilityService.setStrings(model,request);
        List<Station> stationList = stationService.getPaginatedStationList(page);
        model.addAttribute("currentPage",page);
        model.addAttribute("stations",stationList);
        return "stations.html";
    }
    @GetMapping("/addPage")
    public String addPage(Model model, HttpServletRequest request){
        utilityService.addUserInformation(model, request);
        utilityService.setStrings(model,request);
        String error = (String) request.getSession().getAttribute("error");
        if(error!=null){
            model.addAttribute("StationInDB",true);
            request.getSession().setAttribute("error",null);
        }
        return "AddStation";
    }
    @PostMapping("/add")
    public String add(String name,HttpServletRequest request){
       try{ stationService.addStation(name); }catch (IllegalArgumentException e){
           request.getSession().setAttribute("error",e.getMessage());
           return "redirect:/stations/addPage";
       }
        return "redirect:/";
    }
}
