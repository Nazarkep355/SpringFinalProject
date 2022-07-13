package com.example.springproject.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long owner;
    @Column
    private Long train;
    @Column
    private int cost;
    @Column
    private Date date;
    @Column
    private String firstStation;
    @Column
    private String lastStation;
    @Column
    private boolean enable;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Long getTrain() {
        return train;
    }

    public void setTrain(Long train) {
        this.train = train;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        if(Math.abs(ticket.date.compareTo(this.date))>1000)return false;
        return cost == ticket.cost && enable == ticket.enable && Objects.equals(id, ticket.id) && Objects.equals(owner, ticket.owner) && Objects.equals(train, ticket.train) && Objects.equals(firstStation, ticket.firstStation) && Objects.equals(lastStation, ticket.lastStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, train, cost, date, firstStation, lastStation, enable);
    }

    public String getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(String from) {
        this.firstStation = from;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public static class TicketBuilder{
        private Long id;

        private Long owner;

        private Long train;

        private int cost;

        private Date date;

        private String firstStation;

        private String lastStation;

        public TicketBuilder id(Long id){

            this.id=id;
            return this;
        }
        public TicketBuilder owner(Long owner){
            this.owner = owner;
            return this;
        }
        public TicketBuilder train(Long train){
            this.train =train;
            return this;
        }
        public TicketBuilder cost(int cost){
            this.cost=cost;
            return this;
        }
        public TicketBuilder date(Date date){
            this.date=date;
            return this;
        }
        public TicketBuilder firstStation(String from){
            this.firstStation =from;
            return this;
        }
        public TicketBuilder lastStation(String to){
            this.lastStation=to;
            return this;
        }
        public Ticket build(){
            Ticket ticket = new Ticket();
            ticket.setCost(cost);
            ticket.setFirstStation(firstStation);
            ticket.setLastStation(lastStation);
            ticket.setDate(date);
            ticket.setTrain(train);
            ticket.setId(id);
            ticket.setOwner(owner);
            return ticket;
        }
    }
}
