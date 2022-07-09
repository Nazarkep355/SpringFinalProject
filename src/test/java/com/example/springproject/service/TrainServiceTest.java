package com.example.springproject.service;

import com.example.springproject.Entity.Route;
import com.example.springproject.Entity.Train;
import com.example.springproject.Repository.*;
import com.example.springproject.Services.TrainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrainServiceTest {
    private static final long ID = 1l;
    private static TrainService trainService;
    private static TrainRepository trainRepository;
    private static RouteRepository routeRepository;
    @Mock
    private static TicketRepository ticketRepository;
    private static UserRepository userRepository;
    private static StationRepository stationRepository;
    private static  List<Train> trains;
    public static Route route;
    public TrainServiceTest(){
         trainRepository= mock(TrainRepository.class);
         routeRepository =mock(RouteRepository.class);
         userRepository=mock(UserRepository.class);
         stationRepository=mock(StationRepository.class);
        trains = new ArrayList<>();
        List<String> stationList = new ArrayList<>();
        stationList.add("Station1");
        stationList.add("Station2");
        stationList.add("Station3");
        List<Date> dateList = new ArrayList<>();
        Date date = new Date(122,0,15,12,30);
        for(int i =0;i<3;i++){
            Date d = new Date(date.getTime());
            d.setMinutes(date.getMinutes()+i*30);
            dateList.add(d);
        }
        Route route = new Route();
        route.setId(ID);
        route.setStations(stationList);
        Train train = new Train();
        train.setId(ID);
        train.setRoute(route.getId());
        train.setStations(route.getStations());
        train.setAgenda(dateList);
        trains.add(train);
        trainService = new TrainService(trainRepository,routeRepository,ticketRepository,userRepository);
    }
    @Test
    void isExistTrainWithId_ShouldReturnTrue_WhenIdExists(){
        Mockito.when(trainRepository.findTrainById(ID)).thenReturn(trains);
        Assertions.assertTrue(trainService.isExistTrainWithId(ID));
        Assertions.assertFalse(trainService.isExistTrainWithId(2l));

    }
    @Test
    void getTrainById_ShouldReturnTrain_WhenExists(){
        when(trainRepository.findTrainById(ID)).thenReturn(trains);
         Train expected = trains.get(0);
        Train actual = trainService.getTrainById(ID);
        Assertions.assertEquals(expected,actual);
        Assertions.assertThrows(IllegalArgumentException.class,()->trainService.getTrainById(2l),"NoTrainWithId");

    }
    @Test
    void findTrainsByStation_ShouldReturnListOfTrain_IfContainStation(){
        when(trainRepository.findTrainsByStationsContainingOrderByIdDesc("Station1", PageRequest.of(0,5))).thenReturn(trains);
      List<Train> trainList= trainService.findTrainsByStation("Station1",1);
      Assertions.assertTrue(trainList.get(0).getStations().contains("Station1"));
      Assertions.assertFalse(trainList.get(0).getStations().contains("Station4"));
    }
    @Test
    void getTrainsFilteredByStations_ShouldReturnListOfTrain_IfContainStations(){
        when(trainRepository.findTrainsByEnableOrderById(true)).thenReturn(trains);
        List<Train> filteredList = trainService.getTrainsFilteredByStations("Station1","Station2");
        Assertions.assertTrue(filteredList.size()==1);
        filteredList = trainService.getTrainsFilteredByStations("Station2","Station3");
        Assertions.assertTrue(filteredList.size()==1);
        filteredList = trainService.getTrainsFilteredByStations("Station3","Station2");
        Assertions.assertTrue(filteredList.size()==0);
    }
    @Test
    void getTrainsFilteredByStationsAndDate(){
        when(trainRepository.findTrainsByEnableOrderById(true)).thenReturn(trains);
        List<Train> filteredList = trainService.getTrainsFilteredByStationsAndDate("Station1","Station2",
                "2022-01-15");
        Assertions.assertTrue(filteredList.size()==1);
        filteredList = trainService.getTrainsFilteredByStationsAndDate("Station2","Station3",
                "2022-01-15");
        Assertions.assertTrue(filteredList.size()==1);
        filteredList = trainService.getTrainsFilteredByStationsAndDate("Station3","Station2","2022-01-15");
        Assertions.assertTrue(filteredList.size()==0);
    }
    @Test
    void getPaginatedTrains_ShouldReturn(){
        when(trainRepository.findAllByEnableOrderById(true,PageRequest.of(0,5)))
                .thenReturn(trains);
        when(trainRepository.findAllByEnableOrderById(true,PageRequest.of(1,5)))
                .thenReturn(new ArrayList<>());
        List<Train> trainList = trainService.getPaginatedTrains(1);
        Assertions.assertTrue(trainList.size()==1);
        trainList=trainService.getPaginatedTrains(2);
        Assertions.assertTrue(trainList.size()==0);

    }

}
