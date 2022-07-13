package com.example.springproject.utility;

import com.example.springproject.entity.Route;
import com.example.springproject.entity.Train;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainUtility {
    public static Date getDateFromForm(String dateStr){
        Date date= new Date();
        date.setTime(0);
        if(!dateStr.contains("T")){
            String[]segments = dateStr.split("-");
            date.setYear(Integer.parseInt(segments[0])-1900);
            date.setMonth(Integer.parseInt(segments[1])-1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            return date;
        }else {
            String[] segments= dateStr.split("T");
            String[] hoursMins=segments[1].split(":");
            segments = segments[0].split("-");
            date.setYear(Integer.parseInt(segments[0])-1900);
            date.setMonth(Integer.parseInt(segments[1])-1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(Integer.parseInt(hoursMins[0]));
            date.setMinutes(Integer.parseInt(hoursMins[1]));
            date.setSeconds(0);
            return date;}
    }
    public static Train planTrainOnDate(Date date, Route route, int seats){
        List<String> stations = route.getStations();
        List<Date> agenda = new ArrayList<>();
        agenda.add(date);
        int inRoadTime=0;
        for(int delay: route.getDelays()){
            inRoadTime+=delay;
            Date nextStationArrivingDate = new Date(date.getTime());
            nextStationArrivingDate.setMinutes(nextStationArrivingDate.getMinutes()+inRoadTime);
            agenda.add(nextStationArrivingDate);
        }
        Train train = new Train();
        train.setAgenda(agenda.stream().toList());
        train.setStations(stations.stream().toList());
        train.setBooked(0);
        train.setSeats(seats);
        train.setCost(route.getCost());
        train.setRoute(route.getId());
        train.setEnable(true);
        return train;
    }
    public static String fromTo(Train train){
        return train.getStations().get(0)+" - "+ train.getStations().get(train.getStations().size()-1);
    }
    static public String dateToString(Date date){
        String mins = (date.getMinutes()>9)?(""+date.getMinutes()):("0"+date.getMinutes());
        String hours= (date.getHours()>9)?(""+date.getHours()):("0"+date.getHours());
        String days = (date.getDate()>9)?(""+date.getDate()):("0"+date.getDate());
        String months=((date.getMonth()+1)>9)?(""+(date.getMonth()+1)):("0"+(date.getMonth()+1));
        return String.format("%s/%s/%s %s:%s",date.getYear()+1900,months,
                days,hours,mins);
    }
}
