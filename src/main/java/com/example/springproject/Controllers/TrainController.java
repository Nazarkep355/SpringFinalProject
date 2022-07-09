package com.example.springproject.Controllers;

import com.example.springproject.Entity.Train;
import com.example.springproject.Services.RouteService;
import com.example.springproject.Services.TrainService;
import com.example.springproject.Services.UtilityService;
import com.example.springproject.Utility.GeneralUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/trains")
public class TrainController {
    @Autowired
    UtilityService utilityService;
    @Autowired
    TrainService trainService;
    @Autowired
    RouteService routeService;

    @GetMapping("/{id}")
    String getTrainInfo(Model model, HttpServletRequest request,
                        @PathVariable("id")String trainId, String station1,String station2){
        String error = (String) request.getSession().getAttribute("error");
        if(error!=null){
            model.addAttribute("error",error);
            request.getSession().setAttribute("error",null);
        }
        utilityService.setStrings(model,request);
        utilityService.addUserInformation(model,request);
        Train train = null;
               try{ train =trainService.getTrainById(Long.parseLong(trainId));}
               catch (IllegalArgumentException e){
                   request.getSession().setAttribute("error","NoTrainWithId");
                   return "redirect:/";}
        if(station1!=null&&station2!=null){
            model.addAttribute("station1",station1);
            model.addAttribute("station2",station2);
        }
        model.addAttribute("train",train);
        model.addAttribute("agenda",train.getAgenda());
        model.addAttribute("stations",train.getStations());
        return "InfoAboutTrain";
    }
    @GetMapping("/all")
    String getListOfTrains(Model model, HttpServletRequest request,int page){
        utilityService.addUserInformation(model,request);
        utilityService.setStrings(model,request);
        List<Train> trains = trainService.getPaginatedTrains(page);
        model.addAttribute("trains",trains);
        model.addAttribute("currentPage",page);
        return "Trains";
    }
    @GetMapping("/redirToTrain")
    public String redirectToTrain(String train, String station1, String station2){
        if(station1!=null&&station2!=null)
        return "redirect:/trains/"+train+"?station1="+station1+"&station2="+station2;
        else
            return "redirect:/trains/"+train;
    }

    @GetMapping("/planPage")
    public String planTrainPage(Model model,HttpServletRequest request){
        model.addAttribute("routes",routeService.getAllRoutes());
        utilityService.setStrings(model,request);
        utilityService.addUserInformation(model,request);
        return "PlanTrain";
    }
    @PostMapping("/plan")
    public String planTrain(String date, Long route, int seats){
       Train train= trainService.makeTrainFromDateAndRoute(date,route,seats);
       trainService.saveTrain(train);
       train = trainService.getLastTrain();
        return "redirect:/trains/"+train.getId();
    }
    @GetMapping("/findWith")
    public String findWithPage(Model model, HttpServletRequest request, String station1,
                               String station2, String date,int page){
        utilityService.addUserInformation(model,request);
        utilityService.setStrings(model,request);
        List<Train> trains =null;
        if(date==null||date.equals(""))
        trains=trainService.getTrainsFilteredByStations(station1,station2);
        else { trains = trainService.getTrainsFilteredByStationsAndDate(station1, station2, date);
            model.addAttribute("date",date);
        }if(trains.size()>5) { trains = GeneralUtility.paginateList(trains, page);
            model.addAttribute("pageable",true);
            model.addAttribute("currentPage",page);}
        model.addAttribute("station1",station1);
        model.addAttribute("station2",station2);
        model.addAttribute("trains",trains);
        return "Trains";
    }
    @GetMapping("/byStation")
    public String findByStation(Model model, HttpServletRequest request,String station, int page){
        utilityService.setStrings(model,request);
        utilityService.addUserInformation(model, request);
        List<Train> trainList = trainService.findTrainsByStation(station,page);
        model.addAttribute("station",station);
        model.addAttribute("trains",trainList);
        model.addAttribute("currentPage",page);
        return "Trains";
    }

    @PostMapping("/cancel")
    public String cancelTrain(Long train, HttpServletRequest request){
        trainService.cancelTrain(train,request);
        return "redirect:/";
    }

}
