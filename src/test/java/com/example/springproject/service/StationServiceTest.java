package com.example.springproject.service;

import com.example.springproject.entity.Station;
import com.example.springproject.repository.StationRepository;
import com.example.springproject.services.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StationServiceTest {
    StationRepository stationRepository;
    List<Station> stationList;
    StationService stationService;

    public StationServiceTest() {
        stationRepository = mock(StationRepository.class);
        stationService = new StationService(stationRepository);
        stationList = new ArrayList<>();
        stationList.add(new Station("station1"));
        stationList.add(new Station("station2"));
        stationList.add(new Station("station3"));
        stationList.add(new Station("station4"));
    }

    @Test
    void getPaginatedStationListTest() {
        when(stationRepository.findAll(PageRequest.of(0, 5))).thenReturn(stationList.subList(0, 2));
        when(stationRepository.findAll(PageRequest.of(1, 5))).thenReturn(stationList.subList(2, 4));
        List<Station> stations = stationService.getPaginatedStationList(1);
        Assertions.assertEquals(2, stations.size());
        Assertions.assertEquals(new Station("station1"), stations.get(0));
        Assertions.assertEquals(new Station("station2"), stations.get(1));
        stations = stationService.getPaginatedStationList(2);
        Assertions.assertEquals(2, stations.size());
        Assertions.assertEquals(new Station("station3"), stations.get(0));
        Assertions.assertEquals(new Station("station4"), stations.get(1));
    }

    @Test
    void isExistInDataBaseTest() {
        when(stationRepository.findStationByName("station1"))
                .thenReturn(Optional.of(stationList.get(0)));
        when(stationRepository.findStationByName("station2"))
                .thenReturn(Optional.of(stationList.get(1)));
        when(stationRepository.findStationByName("station3"))
                .thenReturn(Optional.of(stationList.get(2)));
        when(stationRepository.findStationByName("station4"))
                .thenReturn(Optional.of(stationList.get(3)));
        Assertions.assertTrue(stationService.isExistInDataBase("station1"));
        Assertions.assertTrue(stationService.isExistInDataBase("station3"));
        Assertions.assertFalse(stationService.isExistInDataBase("station5"));
    }

}
