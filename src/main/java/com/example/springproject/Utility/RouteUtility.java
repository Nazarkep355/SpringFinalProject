package com.example.springproject.Utility;

import com.example.springproject.Entity.Route;

public class RouteUtility {

    public static int fullTimeInRoute(Route route){
        return route.getDelays().stream().mapToInt(Integer::valueOf).sum();
    }
    public static String fromTo(Route route){
        return ""+route.getStations().get(0)+" - "+route.getStations().get(route.getStations().size()-1);
    }
    public static String getStringOfRoute(Route route){
        StringBuilder stationStringBuilder = new StringBuilder();
        for(String s : route.getStations())
            stationStringBuilder.append(s+"-");
        return stationStringBuilder.substring(0,stationStringBuilder.length()-1);
    }
}
