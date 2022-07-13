package com.example.springproject.controllers;

import com.example.springproject.entity.Route;
import com.example.springproject.services.RouteService;
import com.example.springproject.services.UtilityService;
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
@RequestMapping("/routes")
public class RouteController {
    public RouteController(UtilityService utilityService, RouteService routeService) {
        this.utilityService = utilityService;
        this.routeService = routeService;
    }

    @Autowired
    UtilityService utilityService;
    @Autowired
    RouteService routeService;

    @GetMapping("/createPage")
    public String createRoutePage(Model model, HttpServletRequest request) {
        utilityService.setStrings(model, request);
        utilityService.addUserInformation(model, request);
        return "CreateRoute.html";
    }

    @GetMapping("/createP2")
    public String createRoutePart2(Model model, HttpServletRequest request, int number, int cost) {
        model.addAttribute("numberOfStations", number);
        model.addAttribute("cost", cost);
        utilityService.addUserInformation(model, request);
        utilityService.setStrings(model, request);
        return "PlanRoute.html";
    }

    @PostMapping("/create")
    public String createOperation(Model model, HttpServletRequest request) {
        Route route = routeService.getRouteFromForm(request);
        routeService.saveRoute(route);
        utilityService.setStrings(model, request);
        routeService.saveRoute(route);
        return "redirect:/routes/all?page=1";
    }

    @GetMapping("/all")
    public String allRoutes(Model model, HttpServletRequest request, int page) {
        List<Route> routes = routeService.getPaginatedRoutes(page);
        model.addAttribute("routes", routes);
        model.addAttribute("currentPage", page);
        utilityService.setStrings(model, request);
        utilityService.addUserInformation(model, request);
        return "Routes.html";
    }

    @GetMapping("/{id}")
    String getRouteInfo(Model model, HttpServletRequest request,
                        @PathVariable("id") Long routeId, String date, Integer seats) {
        utilityService.setStrings(model, request);
        utilityService.addUserInformation(model, request);
        Route route = null;
        try {
            route = routeService.findRoute(routeId);
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", e.getMessage());
            return "redirect:/";
        }
        if (date != null) {
            model.addAttribute("train", routeService.calculateTrain(date, seats, route));
            model.addAttribute("date", date);
            model.addAttribute("seats", seats);
        }
        model.addAttribute("route", route);
        return "RouteInfo.html";
    }


}
