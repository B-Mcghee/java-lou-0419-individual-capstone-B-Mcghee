package com.tsg.superherosighting.dao;

import com.tsg.superherosighting.dto.*;

import java.util.List;

public interface SuperHeroDao {

    Location addLocation(Location location);
    Location getLocation(int id);
    List<Location> getAllLocations();
    void updateLocation(Location location);
    void removeLocation(int id);

    Organization addOrganization(Organization organization);
    Organization getSingleOrganization(int id);
    List<Organization>getAllOrganizations();
    void updateOrganization(Organization organization);
    void removeOrganization(int id);

    Sighting addSighting(Sighting sighting);
    Sighting getSingleSighting(int id);
    List<Sighting> getAllSightings();
    void updateSighting(Sighting sighting);
    void removeSighting(int id);

    SuperHero addSuperHero(SuperHero superHero);
    SuperHero getSuperHero(int id);
    List<SuperHero>getAllSuperHeros();
    void updateSuperHero(SuperHero superHero);
    void removeSuperHero(int id);

    SuperPower addSuperPower(SuperPower superPower);
    SuperPower getSuperPower(int id);
    List<SuperPower> getAllSuperPower();
    void updateSuperPower(SuperPower superPower);
    void removeSuperPower(int id);

}
