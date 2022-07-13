package com.example.springproject.utility;

import java.util.List;

public class GeneralUtility {

    final static int SECONDS_IN_MINUTE = 60;

    static public String MinutesToStringTime(int minutes) {
        int hours = minutes / SECONDS_IN_MINUTE;
        minutes = minutes - hours * 60;
        return "" + hours + ":" + ((minutes < 10) ? "0" + minutes : minutes);
    }

    public static List paginateList(List trains, int page) {
        page--;
        trains = trains.subList(page * 5, page * 5 + 5);
        return trains;
    }
}
