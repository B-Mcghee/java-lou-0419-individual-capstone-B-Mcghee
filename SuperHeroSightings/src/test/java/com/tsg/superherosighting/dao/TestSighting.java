package com.tsg.superherosighting.dao;

import com.tsg.superherosighting.dto.Location;
import com.tsg.superherosighting.dto.Sighting;
import com.tsg.superherosighting.dto.SuperHero;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSighting {

    @Autowired
    SuperHeroDaoImpl sightingDao;

    private Location[] testLocations = {
            new Location(1,"Bank", "fifth-third", "123 Main Street",new BigDecimal("38.25551600"), new BigDecimal("-85" +
                    ".75872300")),
            new Location(2,"school", "High-school", "321 School Highway",new BigDecimal("-85.25551600"), new BigDecimal(
                    "-85" +
                            ".75872300")),
            new Location(3,"Dominoes", "Pizza Heaven", "456 10th Street",new BigDecimal("66.25551600"), new BigDecimal(
                    "-25" +
                            ".75872300")),
    };
    Location location1 = testLocations[0];
    Location location2 = testLocations[1];
    Location location3 = testLocations[2];
    private SuperHero[] testSuperHeroes = {
            new SuperHero(1,"SuperMan", "S on his chest",false,new ArrayList<>()),
            new SuperHero(2,"Batman", "like a bat",false,new ArrayList<>()),
            new SuperHero(3,"Wolverine", "metal claws",true,new ArrayList<>())
    };
    private Sighting[] testSightings = {
            new Sighting(1, location1, LocalDateTime.now(), new ArrayList<>()),
            new Sighting(2, location2, LocalDateTime.now().minusDays(100), new ArrayList<>()),
            new Sighting(3, location1, LocalDateTime.now().plusDays(100), new ArrayList<>()),
            new Sighting(4, location3, LocalDateTime.now().plusDays(15), new ArrayList<>())
    };

    SuperHero superhero = testSuperHeroes[0];
    SuperHero superhero2 = testSuperHeroes[1];
    SuperHero superhero3 = testSuperHeroes[2];
    
    Sighting sighting = testSightings[0];
    Sighting sighting2 = testSightings[1];
    Sighting sighting3 = testSightings[2];
    Sighting sighting4 = testSightings[3];

    @Before
    public void setUp() {
        JdbcTemplate heySQL = sightingDao.getHeySQL();
        heySQL.execute("DELETE FROM members WHERE id > 0");
        heySQL.execute("DELETE FROM herosightings WHERE id > 0");

        heySQL.execute("DELETE FROM heropowers WHERE id > 0");
        heySQL.execute("DELETE FROM sightings WHERE id > 0");
        heySQL.execute("DELETE FROM superpowers WHERE id > 0");
        heySQL.execute("DELETE FROM superheroes WHERE id > 0");
        heySQL.execute("DELETE FROM Organization WHERE id > 0");

        heySQL.execute("DELETE FROM Locations WHERE id > 0");
        /**
         *    _____ __  ______  __________  __  ____________  ____
         *   / ___// / / / __ \/ ____/ __ \/ / / / ____/ __ \/ __ \
         *   \__ \/ / / / /_/ / __/ / /_/ / /_/ / __/ / /_/ / / / /
         *  ___/ / /_/ / ____/ /___/ _, _/ __  / /___/ _, _/ /_/ /
         * /____/\____/_/   /_____/_/ |_/_/ /_/_____/_/ |_|\____/
         *
         *
         */
        String ADD_SUPERHERO = "INSERT INTO superheroes(id, name, description, isvillian)" +
                "VALUES(?,?,?,?)";
        for (
                SuperHero superHero : testSuperHeroes) {
            heySQL.update(ADD_SUPERHERO, superHero.getId(), superHero.getName(), superHero.getDescription(), superHero.isVillian());
        }
        String INSERT_INTO_LOCATION = "INSERT INTO Locations(id,name, description, address, latitude, longitude)" +
                "VALUES(?,?,?,?,?,?)";
        for (Location aLocation: testLocations){
            heySQL.update(INSERT_INTO_LOCATION,aLocation.getId(), aLocation.getName(), aLocation.getDescription(), aLocation.getAddress(),aLocation.getLatitude(), aLocation.getLongitude());
        }
           String INSERT_INTO_SIGHTINGS = "INSERT INTO sightings(id, locationid, date) VALUES(?,?,?)";
        for (Sighting aSighting: testSightings){
            heySQL.update(INSERT_INTO_SIGHTINGS,aSighting.getId(), aSighting.getLocation().getId(),aSighting.getDate());
        }




        sighting.addSighting(superhero);
        sighting.addSighting(superhero3);
        sighting2.addSighting(superhero2);
        sighting2.addSighting(superhero3);
        sighting3.addSighting(superhero);
        sighting3.addSighting(superhero2);
        sighting4.addSighting(superhero);
        sighting4.addSighting(superhero3);
        sighting4.addSighting(superhero2);

                String INSERT_HEROSIGHTINGS = "INSERT INTO heroSightings(id, heroid, sightingId) VALUES(?,?,?)";
        heySQL.update(INSERT_HEROSIGHTINGS, 1, 1, 1);
        heySQL.update(INSERT_HEROSIGHTINGS, 2, 3, 1);
        heySQL.update(INSERT_HEROSIGHTINGS, 3, 2, 2);
        heySQL.update(INSERT_HEROSIGHTINGS, 4, 3, 2);
        heySQL.update(INSERT_HEROSIGHTINGS, 5, 1, 3);
        heySQL.update(INSERT_HEROSIGHTINGS, 6, 2, 3);
        heySQL.update(INSERT_HEROSIGHTINGS, 7, 1, 4);
        heySQL.update(INSERT_HEROSIGHTINGS, 8, 2, 4);
        heySQL.update(INSERT_HEROSIGHTINGS, 9, 3, 4);
    }

    @Test
    public void testAddAndGetSighting() {

        Sighting sight = testSightings[0];

        sightingDao.addSighting(sight);
        List<Sighting> allSightings = sightingDao.getAllSightings();

        assertNotNull("List should never be null", allSightings);
        assertEquals("should only have 4 organization in list",5 , allSightings.size());
        Sighting testSight= sightingDao.getSingleSighting(sight.getId());
        assertEquals("should equal each other", sight,testSight );

    }
}
