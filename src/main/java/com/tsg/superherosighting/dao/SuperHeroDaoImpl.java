package com.tsg.superherosighting.dao;


import com.tsg.superherosighting.dto.*;
import com.tsg.superherosighting.mappers.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class SuperHeroDaoImpl implements SuperHeroDao {

    @Autowired
    @Getter
    private JdbcTemplate heySQL;


    /**
     * _      ____   _____       _______ _____ ____  _   _
     * | |    / __ \ / ____|   /\|__   __|_   _/ __ \| \ | |
     * | |   | |  | | |       /  \  | |    | || |  | |  \| |
     * | |   | |  | | |      / /\ \ | |    | || |  | | . ` |
     * | |___| |__| | |____ / ____ \| |   _| || |__| | |\  |
     * |______\____/ \_____/_/    \_\_|  |_____\____/|_| \_|
     */

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_Location_INTO_Locations = "INSERT INTO Locations(name, description, address, latitude,longitude) VALUES(?,?,?,?,?) ";
        heySQL.update(INSERT_Location_INTO_Locations, location.getName(), location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude());
        int newId = heySQL.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public Location getLocation(int id) {
        try {
            final String GET_A_SINGLE_LOCATION = "SELECT * FROM Locations WHERE id = ?";
            return heySQL.queryForObject(GET_A_SINGLE_LOCATION, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM Locations";
        return heySQL.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public void updateLocation(Location location) {
        final String UPDATE_A_LOCATION = "UPDATE Locations SET name = ?,description = ?,address = ?,latitude = ?,longitude = ? WHERE id = ?";
        heySQL.update(UPDATE_A_LOCATION, location.getName(), location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude(), location.getId());

    }

    @Override
    @Transactional
    public void removeLocation(int id) {

        final String DELETE_HEROSIGHTING = "DELETE FROM HeroSightings WHERE HeroSightings.sightingid = ?";

        for (Sighting i : sightingsForLocation(id)){
            heySQL.update(DELETE_HEROSIGHTING,i.getId() );
        }

        final String DELETE_MEMBERS = "DELETE FROM Members  where Members.organizationid = ?";
        for (Organization i : locationForOganization(id)) {
            heySQL.update(DELETE_MEMBERS, i.getId());
        }



        this.setLocationForOrganization(id);
        this.setLocationForSighting(id);
        final String DELETE_FROM_LOCATIONS = "DELETE FROM Locations WHERE id = ?";
        heySQL.update(DELETE_FROM_LOCATIONS, id);

    }

    private List<Sighting> sightingsForLocation(int id) {
        final String GET_SIGHTINGS_FOR_LOCATION = "select Sightings.*  from Locations JOIN Sightings ON Locations.id = Sightings.LocationId where Locations.id = ?";
        List<Sighting> sights = heySQL.query(GET_SIGHTINGS_FOR_LOCATION, new SightingMapper(), id);
        return sights;
    }
    private List<Organization> locationForOganization(int id) {
        final String GET_SIGHTINGS_FOR_LOCATION = "select organization.*  from Locations JOIN organization ON Locations.id = organization.LocationId where Locations.id = ?";
        List<Organization> places = heySQL.query(GET_SIGHTINGS_FOR_LOCATION, new OrganizationMapper(), id);
        return places;
    }

    private void removeAllLocationsFromForeignKeys(int locationId) {
        String REMOVE_LOCATION_FROM_KEYS = "DELETE FROM bloghash WHERE HashtagId = ?";
        heySQL.update(REMOVE_LOCATION_FROM_KEYS, locationId);
    }
    private void setLocationForOrganization(int locationId) {
        String UPDATE_LOCATION= "UPDATE Organization SET LocationId = '-1' WHERE LocationId = ?";
        heySQL.update(UPDATE_LOCATION, locationId);
    }
    private void setLocationForSighting(int locationId) {
        String UPDATE_LOCATION= "UPDATE Sightings SET LocationId = '-1' WHERE LocationId = ?";
        heySQL.update(UPDATE_LOCATION, locationId);
    }

    /**
     * ____    _____   _____          _   _ _____ ______      _______ _____ ____  _   _
     * / __ \ |  __ \ / ____|    /\  | \ | |_   _|___  /   /\|__   __|_   _/ __ \| \ | |
     * | |  | | |__) | |  __   /  \  |  \| | | |    / /   /  \  | |    | || |  | |  \| |
     * | |  | |  _  /| | |_ | / /\ \ | . ` | | |   / /   / /\ \ | |    | || |  | | . ` |
     * | |__| | | \ \| |__| |/ ____ \| |\  |_| |_ / /__ / ____ \| |   _| || |__| | |\  |
     * \____/|_|  \_\\_____/_/    \_\_| \_|_____/_____/_/    \_\_|  |_____\____/|_| \_|
     */


    @Override
//    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_INTO_ORGANIZATIONS = "INSERT INTO `Organization`(name,description, LocationId) VALUES(?,?,?) ";
//        Location location = getLocationIdforOrganization(organization.getId());
        heySQL.update(INSERT_INTO_ORGANIZATIONS, organization.getName(), organization.getDescription(), organization.getLocation().getId());

        int newId = heySQL.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        addMembersToOrganization(organization.getMembers(), organization.getId());
        return organization;
    }

    public void addMemberToOrganization(int superheroId, int orgId) {
        final String INSERT_INTO_MEMBERS = "INSERT INTO Members(Heroid, organizationid) VALUES(?,?)";
        heySQL.update(INSERT_INTO_MEMBERS, superheroId, orgId);
    }

    private void addMembersToOrganization(List<SuperHero> heroes, int orgid) {

        for (SuperHero hero : heroes) {
            this.addMemberToOrganization(hero.getId(), orgid);
        }
    }


    @Override
    public Organization getSingleOrganization(int id) {
        try {
            final String SELECT_ORGANIZATION = "SELECT * FROM organization where id = ?";
            Organization organization = heySQL.queryForObject(SELECT_ORGANIZATION, new OrganizationMapper(), id);
            organization.setLocation(getLocationIdforOrganization(id));
            organization.setMembers(getHeroesForOrganization(id));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationIdforOrganization(int id) {
        final String GET_LOCATION_ID = "SELECT l.* FROM Locations l JOIN organization o ON l.id = o.LocationId WHERE o.id = ?";
        return heySQL.queryForObject(GET_LOCATION_ID, new LocationMapper(), id);

    }

    private List<SuperHero> getHeroesForOrganization(int id) {
        final String SELECT_HEROES_FOR_ORGANIZATION = "SELECT SuperHeroes.* FROM SuperHeroes " +
                "JOIN Members ON SuperHeroes.id = Members.Heroid WHERE Members.Organizationid = ?";

        List<SuperHero> heroes = heySQL.query(SELECT_HEROES_FOR_ORGANIZATION, new SuperHeroMapper(), id);
        for (SuperHero hero : heroes) {
            hero.setHeroPowers(getSuperHeroPowers(hero.getId()));
        }
        return heroes;
    }


    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM Organization";
        List<Organization> organizations = heySQL.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());

        for (Organization i : organizations) {
            i.setMembers(this.getHeroesForOrganization(i.getId()));
            i.setLocation(this.getLocationIdforOrganization(i.getId()));
        }
        return organizations;
    }

    @Override
    public void updateOrganization(Organization organization) {

        final String UPDATE_ORGANIZATION = "UPDATE Organization SET " +
                " name = ?, description = ?, LocationId = ? WHERE Organization.id = ?";
        heySQL.update(UPDATE_ORGANIZATION, organization.getName(), organization.getDescription(), organization.getLocation().getId(), organization.getId());

        final String DELETE_MEMBERS = "DELETE FROM Members m WHERE m.organizationId = ?";
        heySQL.update(DELETE_MEMBERS, organization.getId());

        addMembersToOrganization(organization.getMembers(), organization.getId());
    }

    @Override
    public void removeOrganization(int id) {
        final String DELETE_FROM_MEMBERS = "DELETE FROM Members WHERE Members.organizationid = ?";

        heySQL.update(DELETE_FROM_MEMBERS, id);

        final String DELETE_FROM_ORGANIZATIONS = "DELETE FROM Organization WHERE id = ?";
        heySQL.update(DELETE_FROM_ORGANIZATIONS, id);
    }

    /**
     *  _____ _____ _____ _    _ _______ _____ _   _  _____
     * / ____|_   _/ ____| |  | |__   __|_   _| \ | |/ ____|
     * |  (___ | || |  __| |__| |  | |    | | |  \| | |  __
     * \___  \ | || | |_ |  __  |  | |    | | | . ` | | |_ |
     * ____) |_| || |__| | |  | |  | |   _| |_| |\  | |__| |
     * |_____/|_____\_____|_| |_|  |_|  |_____|_| \_|\_____|
     */


    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO Sightings(LocationId, date) VALUES(?,?)";

        heySQL.update(INSERT_SIGHTING, sighting.getLocation().getId(), Timestamp.valueOf(sighting.getDate()).toLocalDateTime());

        int newId = heySQL.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);


        sighting.setId(newId);
        insertIntoHeroSighting(sighting);
        return sighting;

    }


    private void insertIntoHeroSighting(Sighting sighting) {
        final String INSERT_INTO_HEROSIGHTS = "INSERT INTO HeroSightings(Heroid, sightingid) VALUES(?,?)";
        for (SuperHero superHero : sighting.getHeroes()) {
            heySQL.update(INSERT_INTO_HEROSIGHTS, superHero.getId(), sighting.getId());
        }
    }

    @Override
    public Sighting getSingleSighting(int id) {
        try {
            final String GET_SINGLE_SIGHTING = "SELECT * FROM Sightings WHERE id = ?";

            Sighting sighting = heySQL.queryForObject(GET_SINGLE_SIGHTING, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setHeroes(getSuperHeroesForSightings(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String GET_LOCATION_FROM_SIGHTING = "select Locations.* from Sightings  " +
                "JOIN Locations on Locations.id  = Sightings.LocationId where Sightings.id = ?";
        Location location = heySQL.queryForObject(GET_LOCATION_FROM_SIGHTING, new LocationMapper(), id);
        return location;
    }

    private List<SuperHero> getSuperHeroesForSightings(int id) {

        final String GET_HERO_FROM_SIGHTING = "select * from SuperHeroes join HeroSightings on SuperHeroes.id = HeroSightings.Heroid WHERE HeroSightings.sightingid = ?";
        List<SuperHero> heroes = heySQL.query(GET_HERO_FROM_SIGHTING, new SuperHeroMapper(), id);
        for (SuperHero i: heroes){
            i.setHeroPowers(getSuperHeroPowers(i.getId()));
        }
        return heroes;
    }


    public List<Sighting> numOfSightings(int number) {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM Sightings ORDER BY date DESC LIMIT "+ number;
        List<Sighting> sightings = heySQL.query(GET_ALL_SIGHTINGS, new SightingMapper());

        for (Sighting i : sightings) {
            i.setLocation(getLocationForSighting(i.getId()));
            i.setHeroes(getSuperHeroesForSightings(i.getId()));

        }
        return sightings;
    }
    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM Sightings";
        List<Sighting> sightings = heySQL.query(GET_ALL_SIGHTINGS, new SightingMapper());

        for (Sighting i : sightings) {
            i.setLocation(getLocationForSighting(i.getId()));
            i.setHeroes(getSuperHeroesForSightings(i.getId()));

        }
        return sightings;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTINGS = "UPDATE Sightings SET " +
                " LocationId = ?, date = ? WHERE Sightings.id = ?";

        heySQL.update(UPDATE_SIGHTINGS,sighting.getLocation().getId(), Timestamp.valueOf(sighting.getDate()), sighting.getId());
        final String DELETE_SIGHTING = "DELETE FROM HeroSightings WHERE HeroSightings.sightingid = ?";
        heySQL.update(DELETE_SIGHTING, sighting.getId());
        insertIntoHeroSighting(sighting);

    }


    @Override
    public void removeSighting(int id) {
        final String DELETE_FROM_HEROSIGHTING = "DELETE FROM HeroSightings WHERE HeroSightings.sightingid = ?";
        heySQL.update(DELETE_FROM_HEROSIGHTING, id);

        final String DELETE_FROM_SIGHTING = "DELETE FROM Sightings WHERE id = ?";
        heySQL.update(DELETE_FROM_SIGHTING, id);
    }

    /**
     * _____ _    _ _____  ______ _____  _    _ ______ _____   ____
     * / ____| |  | |  __ \|  ____|  __ \| |  | |  ____|  __ \ / __ \
     * | (___| |  | | |__) | |__  | |__) | |__| | |__  | |__) | |  | |
     * \___ \| |  | |  ___/|  __| |  _  /|  __  |  __| |  _  /| |  | |
     * ____) | |__| | |    | |____| | \ \| |  | | |____| | \ \| |__| |
     * |_____/\____/|_|    |______|_|  \_\_|  |_|______|_|  \_\\____/
     */


    @Override
    @Transactional
    public SuperHero addSuperHero(SuperHero superHero) {
        final String INSERT_SUPERHERO = "INSERT INTO SuperHeroes(name, description, isvillian) VALUES (?,?,?)";
        heySQL.update(INSERT_SUPERHERO, superHero.getName(), superHero.getDescription(), superHero.isVillian());

        int newId = heySQL.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        superHero.setId(newId);
        insertHeroesInBridges(superHero);
        return superHero;

    }

    private void insertHeroesInBridges(SuperHero superHero) {
        final String INSERT_INTO_HEROPOWERS = "INSERT INTO HeroPowers(Heroid, powersid) VALUES(?,?)";

        for (SuperPower powers : superHero.getHeroPowers()) {
            heySQL.update(INSERT_INTO_HEROPOWERS, superHero.getId(), powers.getId());
        }
    }

    @Override
    public SuperHero getSuperHero(int id) {
        try {
            final String SELECT_SUPERHERO = "SELECT * FROM SuperHeroes where id = ?";
            SuperHero hero = heySQL.queryForObject(SELECT_SUPERHERO, new SuperHeroMapper(), id);
            hero.setHeroPowers(getSuperHeroPowers(id));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public List<SuperPower> getSuperHeroPowers(int id) {
        final String GET_POWERS_WITH_ID = "SELECT SuperPowers.* FROM SuperPowers JOIN" +
                " HeroPowers ON HeroPowers.powersid = SuperPowers.id where HeroPowers.Heroid = ?";
        List<SuperPower> powers = heySQL.query(GET_POWERS_WITH_ID, new SuperPowerMapper(), id);
        return powers;

    }


    @Override
    public List<SuperHero> getAllSuperHeros() {
        final String GET_ALL_SUPERHEROES = "SELECT * FROM SuperHeroes";
        List<SuperHero> heroes = heySQL.query(GET_ALL_SUPERHEROES, new SuperHeroMapper());

        for (SuperHero i : heroes) {
            i.setHeroPowers(getSuperHeroPowers(i.getId()));

        }
        return heroes;
    }

    @Override
    @Transactional
    public void updateSuperHero(SuperHero superHero) {
        final String UPDATE_SUPERHERO = "UPDATE SuperHeroes SET " +
                " name = ?, description = ?, isvillian = ? WHERE SuperHeroes.id = ?";
        heySQL.update(UPDATE_SUPERHERO, superHero.getName(), superHero.getDescription(), superHero.isVillian(), superHero.getId());
        final String DELETE_HEROPOWERS = "DELETE FROM HeroPowers  WHERE HeroPowers.Heroid = ?";



        heySQL.update(DELETE_HEROPOWERS, superHero.getId());
        insertHeroesInBridges(superHero);

    }

    @Override
    @Transactional
    public void removeSuperHero(int id) {
        final String DELETE_HEROPOWERS = "DELETE FROM HeroPowers  WHERE HeroPowers.Heroid = ?";
        final String DELETE_MEMBERS = "DELETE FROM Members  WHERE Members.Heroid = ?";
        final String DELETE_HEROSIGHTINGS = "DELETE FROM HeroSightings WHERE HeroSightings.Heroid = ?";
        final String DELETE_SUPERHERO = "DELETE FROM SuperHeroes  WHERE id = ?";
        heySQL.update(DELETE_MEMBERS, id);
        heySQL.update(DELETE_HEROPOWERS, id);
        heySQL.update(DELETE_HEROSIGHTINGS, id);
        heySQL.update(DELETE_SUPERHERO, id);

    }


    /**
     * _____ _    _ _____  ______ _____    _____   ______          ________ _____   _____
     * / ____| |  | |  __ \|  ____|  __ \  |  __ \ / __ \ \        / /  ____|  __ \ / ____|
     * | (___| |  | | |__) | |__  | |__) | | |__) | |  | \ \  /\  / /| |__  | |__) | (___
     * \___ \| |  | |  ___/|  __| |  _  /  |  ___/| |  | |\ \/  \/ / |  __| |  _  / \___ \
     * ____) | |__| | |    | |____| | \ \  | |    | |__| | \  /\  /  | |____| | \ \ ____) |
     * |_____/\____/|_|   |______|_|  \_\  |_|     \____/   \/  \/   |______|_|  \_\_____/
     */


    @Override
    public SuperPower addSuperPower(SuperPower superPower) {
        final String INSERT_SUPERPOWER = "INSERT INTO SuperPowers(type) VALUES (?)";
        heySQL.update(INSERT_SUPERPOWER, superPower.getType());

        int newId = heySQL.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        superPower.setId(newId);

        return superPower;
    }

    @Override
    public SuperPower getSuperPower(int id) {
        try {
            final String SELECT_SUPERHERO = "SELECT * FROM SuperPowers where id = ?";
            return heySQL.queryForObject(SELECT_SUPERHERO, new SuperPowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPower() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM SuperPowers";
        return heySQL.query(GET_ALL_SUPERPOWERS, new SuperPowerMapper());
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        final String UPDATE_SUPERPOWER = "UPDATE SuperPowers SET type = ? where id = ?";
        heySQL.update(UPDATE_SUPERPOWER, superPower.getType(), superPower.getId());

    }

    @Override
    public void removeSuperPower(int id) {
        final String DELETE_FROM_HEROPOWERS = "DELETE FROM HeroPowers WHERE HeroPowers.powersid = ?";
        heySQL.update(DELETE_FROM_HEROPOWERS, id);

        final String DELETE_FROM_SUPERPOWERS = "DELETE FROM SuperPowers WHERE id = ?";
        heySQL.update(DELETE_FROM_SUPERPOWERS, id);
    }
    public List<SuperHero> getSuperHeroWithSpecificPower(int id) {
        final String GET_POWERS_WITH_ID = "SELECT SuperHeroes.* from SuperHeroes " +
                "JOIN HeroPowers ON HeroPowers.Heroid = SuperHeroes.id where HeroPowers.powersid = ?";
        List<SuperHero> heroes = heySQL.query(GET_POWERS_WITH_ID, new SuperHeroMapper(), id);
        for (SuperHero hero: heroes) {
                hero.setHeroPowers(getSuperHeroPowers(hero.getId()));
        }

        return heroes;

    }
}
