package com.example.springproject.Utility;

import java.util.List;

public class GeneralUtility {
    static public String MinutesToStringTime(int minutes){
        int hours = minutes/60;
        minutes=minutes-hours*60;
        return ""+hours+":"+((minutes<10)?"0"+minutes:minutes);
    }

    public static List paginateList(List trains, int page){
        page--;
        trains = trains.subList(page*5,page*5+5);
        return trains;
    }
}
