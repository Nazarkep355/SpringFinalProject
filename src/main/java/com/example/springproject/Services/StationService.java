package com.example.springproject.Services;

import com.example.springproject.Entity.Station;
import com.example.springproject.Repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationService {
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Autowired
    StationRepository stationRepository;


    public List<Station> getPaginatedStationList(int page){
        --page;
        return stationRepository.findAll(PageRequest.of(page,5));
    }
    public boolean isExistInDataBase(String name){
        Optional<Station> station = stationRepository.findStationByName(name);
        return station.isPresent();
    }
    public void addStation(String name){
        if(isExistInDataBase(name))
            throw new IllegalArgumentException("StationInDB");
        Station station = new Station(name);
        stationRepository.save(station);
    }

}
