package com.tsg.superherosighting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SuperHero {
    private int id;
    private String name;
    private String description;
    private boolean isVillian;
    private List<SuperPower> heroPowers;

    public void addPower(SuperPower superPower){
        this.heroPowers.add(superPower);
    }

    public boolean getIsVillian(){
        return isVillian;
    }

    public boolean isVillian(){
        return isVillian;
    }

}
