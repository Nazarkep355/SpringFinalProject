package com.example.springproject.util;

import com.example.springproject.entity.Train;
import com.example.springproject.utility.TrainUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainUtilityTest {
    @Test
    void dateToString_ShouldReturnStringInFormat_YYYY_MM_DD_HH_MM(){
        Date date = Date.from(Instant.now());
        date.setHours(12);
        date.setYear(122);
        date.setMonth(11);
        date.setDate(31);
        date.setMinutes(30);
        Assertions.assertEquals("2022/12/31 12:30", TrainUtility.dateToString(date));
        date.setYear(150);
        date.setMonth(6);
        date.setDate(20);
        Assertions.assertEquals("2050/07/20 12:30", TrainUtility.dateToString(date));
    }
    @Test
    void fromTo_ShouldReturnStringWithFirstAndLastStation(){
        Train train = new Train();
        List<String> stations = new ArrayList<>();
        stations.add("Station1");
        stations.add("Station2");
        train.setStations(stations);
        Assertions.assertEquals("Station1 - Station2",TrainUtility.fromTo(train));
        stations.add("Station3");
        Assertions.assertEquals("Station1 - Station3",TrainUtility.fromTo(train));
    }
}
