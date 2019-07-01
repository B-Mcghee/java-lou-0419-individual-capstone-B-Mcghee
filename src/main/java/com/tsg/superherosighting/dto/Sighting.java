package com.tsg.superherosighting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sighting{
    private int id;
    private Location location;
    private LocalDateTime date;
    private List<SuperHero> heroes;



    public void addSighting(SuperHero hero){
        this.heroes.add(hero);
    }

}
