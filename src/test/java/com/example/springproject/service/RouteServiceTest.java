package com.example.springproject.service;

import com.example.springproject.entity.Route;
import com.example.springproject.entity.Train;
import com.example.springproject.repository.RouteRepository;
import com.example.springproject.services.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouteServiceTest {
    List<Route> routes;
    RouteService routeService;
    RouteRepository routeRepository;
    public RouteServiceTest(){
        routeRepository = mock(RouteRepository.class);
        routeService = new RouteService(routeRepository);
        routes = new ArrayList<>();
        Route route = new Route();
        List<String> stations = new ArrayList<>();
        stations.add("Station1");
        stations.add("Station2");
        stations.add("Station3");
        route.setStations(stations);
        List<Integer> delays = new ArrayList<>();
        delays.add(70);
        delays.add(80);
        route.setDelays(delays);
        route.setCost(150);
        route.setId(1l);
        routes.add(route);
        route = new Route();
        stations = new ArrayList<>();
        stations.add("Station1");
        stations.add("Station3");
        stations.add("Station2");
        route.setStations(stations);
        delays = new ArrayList<>();
        delays.add(70);
        delays.add(80);
        route.setDelays(delays);
        route.setCost(150);
        route.setId(2l);
        routes.add(route);
    }
    @Test
    void calculateTrain_ShouldReturnTrainByParameters(){
        Route route = new Route();
        List<String> stations = new ArrayList<>();
        stations.add("Station1");
        stations.add("Station2");
        stations.add("Station3");
        route.setStations(stations);
        List<Integer> delays = new ArrayList<>();
        delays.add(70);
        delays.add(80);
        route.setDelays(delays);
        route.setCost(150);
        route.setId(1l);
        Train train = new Train();
        train.setRoute(route.getId());
        train.setStations(stations);
        train.setCost(150);
        train.setEnable(true);
        train.setBooked(0);
        train.setSeats(700);
        List<Date> dates = new ArrayList<>();
        Date date = new Date();
        date.setTime(0);
        date.setMonth(5);
        date.setYear(122);
        date.setHours(15);
        date.setMinutes(00);
        date.setDate(23);
        date.setSeconds(0);
        dates.add(date);
        date = new Date(date.getTime());
        date.setMinutes(date.getMinutes()+70);
        dates.add(date);
        date = new Date(date.getTime());
        date.setMinutes(date.getMinutes()+80);
        dates.add(date);
        train.setAgenda(dates);
        Train train1 = routeService.calculateTrain("2022-06-23T15:00",700,route);
        Assertions.assertEquals(train,train1);
    }
    @Test
    void findRoute_ShouldReturnOptionalOfRoute(){
        when(routeRepository.getRouteById(1l)).thenReturn(Optional.of(routes.get(0)));
        Assertions.assertTrue(routeRepository.getRouteById(1l).isPresent());
        Assertions.assertEquals(1l,routeRepository.getRouteById(1l).get().getId());
        Assertions.assertFalse(routeRepository.getRouteById(3l).isPresent());
    }
    @Test
    void getAllRoutes_ShouldReturnListOfRoutes(){
        when(routeRepository.findAll()).thenReturn(routes);
        Assertions.assertTrue(routeService.getAllRoutes().get(0).getId()==1l);
        Assertions.assertEquals(2,routeService.getAllRoutes().size());
    }
    @Test
    void getPaginatedRoutes_ShouldReturnSublist(){
        when(routeRepository.findAll(PageRequest.of(0,5)))
                .thenReturn(routes.subList(0,1));
        when(routeRepository.findAll(PageRequest.of(1,5)))
                .thenReturn(routes.subList(1,2));
        Assertions.assertEquals(1,routeService.getPaginatedRoutes(1).size());
        Assertions.assertEquals(routes.get(0),routeService.getPaginatedRoutes(1).get(0));
        Assertions.assertEquals(1,routeService.getPaginatedRoutes(1).size());
        Assertions.assertEquals(routes.get(1),routeService.getPaginatedRoutes(2).get(0));
    }
}
