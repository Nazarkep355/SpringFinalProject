package com.example.springproject.repository;


import com.example.springproject.entity.Station;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface StationRepository extends CrudRepository<Station,String> {
     Optional<Station> findStationByName(String name);
    List<Station> findAll(Pageable pageable);
//    public List<Station> findAll();
}
