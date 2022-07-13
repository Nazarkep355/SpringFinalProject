package com.example.springproject.util;

import com.example.springproject.entity.Route;
import com.example.springproject.utility.RouteUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RouteUtilityTest {
    @Test
    void getStringOfRoute_ShouldReturnStringOfAllStations(){
        List<Integer> delays = new ArrayList<>();
        delays.add(120);
        delays.add(70);
        delays.add(80);
        List<String> stationList = new ArrayList<>();
        stationList.add("Station1");
        stationList.add("Station2");
        stationList.add("Station3");
        stationList.add("Station4");
        Route route = new Route();
        route.setId(1l);
        route.setCost(150);
        route.setStations(stationList);
        route.setDelays(delays);
        Assertions.assertEquals("Station1-Station2-Station3-Station4",RouteUtility.getStringOfRoute(route));
        stationList.add("Station5");
        Assertions.assertEquals("Station1-Station2-Station3-Station4-Station5",RouteUtility.getStringOfRoute(route));
    }
    @Test
    void fromTo_ShouldReturnStringContainsFirstAndLastStation(){
        List<Integer> delays = new ArrayList<>();
        delays.add(120);
        delays.add(70);
        delays.add(80);
        List<String> stationList = new ArrayList<>();
        stationList.add("Station1");
        stationList.add("Station2");
        stationList.add("Station3");
        stationList.add("Station4");
        Route route = new Route();
        route.setId(1l);
        route.setCost(150);
        route.setStations(stationList);
        route.setDelays(delays);
        Assertions.assertEquals("Station1 - Station4",RouteUtility.fromTo(route));
        stationList.add("Station5");
        Assertions.assertEquals("Station1 - Station5",RouteUtility.fromTo(route));
    }
    @Test
    void fullTimeInRoute_ShouldReturnSumOfMinutesInRoute(){
        List<Integer> delays = new ArrayList<>();
        delays.add(120);
        delays.add(70);
        delays.add(80);
        List<String> stationList = new ArrayList<>();
        stationList.add("Station1");
        stationList.add("Station2");
        stationList.add("Station3");
        stationList.add("Station4");
        Route route = new Route();
        route.setId(1l);
        route.setCost(150);
        route.setStations(stationList);
        route.setDelays(delays);
        Assertions.assertEquals(270,RouteUtility.fullTimeInRoute(route));
        delays.add(80);
        Assertions.assertEquals(350,RouteUtility.fullTimeInRoute(route));
    }

}
