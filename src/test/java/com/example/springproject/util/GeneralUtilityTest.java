package com.example.springproject.util;

import com.example.springproject.entity.Train;
import com.example.springproject.utility.GeneralUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtilityTest {
    @Test
    void paginateList_ShouldReturnSublistByPage(){
        List<Train> trainList = new ArrayList<>();
        for(long i =0;i<10;i++){
            Train train = new Train();
            train.setId(i);
            trainList.add(train);
        }
        List<Train> pagList = GeneralUtility.paginateList(trainList,1);
        long id =0;
        for(Train t : pagList){
            Assertions.assertEquals(id,t.getId());
            id++;}
        pagList = GeneralUtility.paginateList(trainList,2);
        for(Train t : pagList){
            Assertions.assertEquals(id,t.getId());
            id++;}
    }
    @Test
    void MinutesToStringTime_ShouldReturnStringInHH_MM_Format(){
        Assertions.assertEquals("1:00",GeneralUtility.MinutesToStringTime(60));
        Assertions.assertNotEquals("0:60",GeneralUtility.MinutesToStringTime(60));
        Assertions.assertEquals("1:27",GeneralUtility.MinutesToStringTime(87));
        Assertions.assertEquals("27:30",GeneralUtility.MinutesToStringTime(1650));

    }
}
