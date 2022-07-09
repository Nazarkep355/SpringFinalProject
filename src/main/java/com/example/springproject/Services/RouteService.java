package com.example.springproject.Services;

import com.example.springproject.Entity.Route;
import com.example.springproject.Entity.Train;
import com.example.springproject.Repository.RouteRepository;
import com.example.springproject.Utility.TrainUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Autowired
    RouteRepository routeRepository;

    public List<Route> getAllRoutes(){
        return (List<Route>) routeRepository.findAll();
    }
    public Route getRouteFromForm(HttpServletRequest request){
        int number = Integer.parseInt(request.getParameter("number"));
        int cost = Integer.parseInt(request.getParameter("cost"));
        List<String> stationList = new ArrayList<>();
        List<Integer> delayList = new ArrayList<>();
        Route route = new Route();
        for(int i = 1;i<=number;i++){
            stationList.add(request.getParameter("station"+i));
            if(i==number)break;
            delayList.add(Integer.parseInt(request.getParameter("delay"+i)));}
        route.setDelays(delayList);
        route.setStations(stationList);
        route.setCost(cost);
        route.setId(routeRepository.findFirstByOrderByIdDesc().get(0).getId()+1);
        return route;
    }
    public Route saveRoute(Route route){
       return routeRepository.save(route);
    }
    public Route findRoute(Long id){
      Optional<Route> routeOptional =routeRepository.getRouteById(id);
      if(routeOptional.isPresent())
          return routeOptional.get();
      else throw new IllegalArgumentException("NoRouteWithId");
    }
    public List<Route> getPaginatedRoutes(int page){
        return routeRepository.findAll(PageRequest.of(--page,5));
    }
    public Train calculateTrain(String dateString,int seats, Route route){
        Date date = TrainUtility.getDateFromForm(dateString);
        return TrainUtility.planTrainOnDate(date,route,seats);
        }
}
