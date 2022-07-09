package com.example.springproject.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Station {
    @Id
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String n){
        name=n;
    }
    public Station(){}
    public Station(String s){
        name =s;
    }

    @Override
    public int hashCode() {
        int res=0;
        for(char c : getName().toCharArray()){
            res+=c*17;
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass())return false;
        return ((com.example.springproject.Entity.Station) obj).getName().equals(this.name);
    }

}
