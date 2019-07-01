package com.tsg.superherosighting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SuperHero {
    private int id;
    private String name;
    private String description;
    private boolean isVillian;
    private List<SuperPower> heroPowers;

    public void addPower(SuperPower superPower){
        this.heroPowers.add(superPower);
    }

}
