package com.example.springproject.controllers;

import com.example.springproject.Controllers.RouteController;
import com.example.springproject.Entity.Route;
import com.example.springproject.Repository.RouteRepository;
import com.example.springproject.Services.RouteService;
import com.example.springproject.Services.UtilityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class RouteControllerTest {
    @Mock
    RouteRepository routeRepository;
    RouteService routeService;
    @Mock
    UtilityService utilityService;
    RouteController controller;
    List<Route> routes;
    public RouteControllerTest(){
        routes = new ArrayList<>();
        Route route = new Route();
        route.setCost(150);
        route.setStations(List.of("First","Second","Third"));
        route.setDelays(List.of(50,50));
        route.setId(1l);
        routes.add(route);
        MockitoAnnotations.initMocks(this);
        routeService = new RouteService(routeRepository);
        controller = new RouteController(utilityService,routeService);
    }
    @Test
    void createRoutePageTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/routes/createPage"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("CreateRoute.html"));
    }

    @Test
    void createRoutePart2()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/routes/createP2")
                        .with(request ->
                {request.setParameter("number","3");
                request.setParameter("cost","150");
                return request;}))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("PlanRoute.html"));
    }

    @Test
    void createOperationTest()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Route route2 = new Route();
        route2.setId(0l);
        when(routeRepository.findFirstByOrderByIdDesc()).thenReturn(List.of(route2));
        mockMvc.perform(MockMvcRequestBuilders.post("/routes/create").with(request ->
        {request.setParameter("cost","150");
         request.setParameter("number","3");
         request.setParameter("station1","First");
         request.setParameter("station2","Second");
         request.setParameter("station3","Third");
         request.setParameter("delay1","50");
         request.setParameter("delay2","50");
         return request;
        })).andExpect(MockMvcResultMatchers.redirectedUrl("/routes/all?page=1"));
    }
    @Test
    void allRoutes() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(routeRepository.findAll(PageRequest.of(0,5))).thenReturn(routes);
        mockMvc.perform(MockMvcRequestBuilders.get("/routes/all").with(request ->
        {request.setParameter("page","1");
        return request;}))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("Routes.html"));
    }
    @Test
    void getRouteInfo() throws Exception {
        Route route = new Route();
        route.setId(1l);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(routeRepository.getRouteById(1l)).thenReturn(Optional.of(route));
        when(routeRepository.getRouteById(2l)).thenReturn(Optional.ofNullable(null));
        //Not found route by id
        mockMvc.perform(MockMvcRequestBuilders.get("/routes/2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        //Found route by id
        mockMvc.perform(MockMvcRequestBuilders.get("/routes/1"))
                .andExpect(MockMvcResultMatchers.view().name("RouteInfo.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("route",route));
    }


}
