package com.example.springproject.repository;

import com.example.springproject.entity.Route;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface RouteRepository extends CrudRepository<Route,Long> {
    Optional<Route> getRouteById(Long id);
    List<Route> findAll(Pageable pageable);
    List<Route> findFirstByOrderByIdDesc();
}
