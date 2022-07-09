package com.example.springproject.controllers;

import com.example.springproject.Controllers.StationsController;
import com.example.springproject.Entity.Station;
import com.example.springproject.Repository.StationRepository;
import com.example.springproject.Services.StationService;
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

import static org.mockito.Mockito.when;

public class StationsControllerTest {
    @Mock
    StationRepository stationRepository;
    List<Station> stationList;
    @Mock
    UtilityService utilityService;
    StationService stationService;
    StationsController controller;
    public StationsControllerTest(){
        MockitoAnnotations.initMocks(this);
        stationService = new StationService(stationRepository);
        controller = new StationsController(utilityService,stationService);
        stationList = new ArrayList<>();
        for(int i=0;i<5;i++){
            stationList.add(new Station("Station"+(i+1)));
        }
    }

    @Test
    void stationListTest() throws Exception {
        when(stationRepository.findAll(PageRequest.of(0,5))).thenReturn(stationList);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/stations/all").with(request ->
        {request.setParameter("page","1");
        return request;}))
                .andExpect(MockMvcResultMatchers.view().name("stations.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("stations",stationList));
    }

}
