package com.example.springproject.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long route;
    @ElementCollection
    private List<String> stations;
    @ElementCollection
    private List<Date> agenda;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        if(train.getStations().size()==((Train) o).stations.size())
        for(int i =0;i<agenda.size();i++){
            if(!stations.get(i).equals(((Train) o).stations.get(i)))
                return false;
            if(agenda.get(i).compareTo(((Train) o).agenda.get(i))!=0)
                return false;
        }
        return cost == train.cost && seats == train.seats && booked == train.booked && enable == train.enable && id== train.id && route==train.route;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, stations, agenda, cost, seats, booked, enable);
    }

    @Column
    private int cost;
    @Column
    private int seats;
    @Column
    private int booked;
    @Column
    private boolean enable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }
    public List<Date> getAgenda() {
        return agenda;
    }
    public void setAgenda(List<Date> agenda) {
        this.agenda = agenda;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public int getBooked() {
        return booked;
    }
    public void setBooked(int booked) {
        this.booked = booked;
    }
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
