package com.tsg.superherosighting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    private int id;
    private String name;
    private String description;
    private Location location;
    private List<SuperHero> members;

    public void addMember(SuperHero hero){
        this.members.add(hero);
    }


    public void addLocation(Location location){
        this.location = location;
    }
}
