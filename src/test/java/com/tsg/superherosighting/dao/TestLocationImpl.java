package com.tsg.superherosighting.dao;


import com.tsg.superherosighting.dto.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestLocationImpl {

    @Autowired
    SuperHeroDaoImpl heroDao;

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

    @Before
    public void setUp(){
        JdbcTemplate heySQL = heroDao.getHeySQL();
        heySQL.execute("DELETE FROM sightings WHERE id > 0");
        heySQL.execute("DELETE FROM members WHERE id > 0");
        heySQL.execute("DELETE FROM organization WHERE id > 0");
        heySQL.execute("DELETE FROM Locations WHERE id > 0");
    }

    @Test
    public void testAddLocation() {
        Location location = new Location(1,"Stadium", "an arena", "999 athlete avenue",new BigDecimal("22.25551600"),
                new BigDecimal(
                "-25.75872300"));

        heroDao.addLocation(location);
        List<Location> allLocations = heroDao.getAllLocations();

        assertNotNull("List should never be null", allLocations);
        assertEquals("should only have 1 location in list", 1, allLocations.size());

        Location testLocation = allLocations.get(0);
        assertEquals("should equal each other", location, testLocation);



    }

    @Test
    public void testGetLocation() {
        Location location = new Location(1,"Stadium", "an arena", "999 athlete avenue",new BigDecimal("22.25551600"),
                new BigDecimal(
                        "-25.75872300"));

        heroDao.addLocation(location);
        Location testLocation = heroDao.getLocation(location.getId());

        assertNotNull("List of locations should never be null", testLocation);
        assertEquals("should equal each other", location, testLocation);

    }

    @Test
    public void testEditLocation() {
        Location location = new Location(1,"Stadium", "an arena", "999 athlete avenue",new BigDecimal("22.25551600"),
                new BigDecimal(
                        "-25.75872300"));
        heroDao.addLocation(location);
        Location toEdit = heroDao.getLocation(location.getId());
        assertEquals("they should equal each other", location, toEdit);

        List<Location>allLocations = heroDao.getAllLocations();
        assertTrue("List should contain location", allLocations.contains(location));
        //get location out and edit it


        location.setName("Baseball Field");
        location.setDescription("turf field");
        assertNotEquals("Locations should not equal each other after changes", location, toEdit);
        heroDao.updateLocation(location);

        assertNotEquals(location, toEdit);

        toEdit = heroDao.getLocation(location.getId());

        assertEquals("after updating location they should equal each other", location, toEdit);



    }

    @Test
    public void testRemoveLocation() {
        Location location = new Location(1,"Stadium", "an arena", "999 athlete avenue",new BigDecimal("22.25551600"),
                new BigDecimal(
                        "-25.75872300"));
        heroDao.addLocation(location);
        Location location2 = new Location(2,"school", "high school", "123 main street",new BigDecimal("88.25551600"),
                new BigDecimal(
                        "-99.75872300"));
        heroDao.addLocation(location2);

        List<Location> allLocations = heroDao.getAllLocations();
        assertTrue("list should be contain both locations",allLocations.contains(location));
        assertTrue("list should be contain both locations",allLocations.contains(location2));

        Location fromDao = heroDao.getLocation(location.getId());

        assertEquals("both objects should equal each other",location, fromDao);

        heroDao.removeLocation(location.getId());
        allLocations = heroDao.getAllLocations();

        assertEquals("list should be contain only location2",1,allLocations.size());
        assertTrue("list should be contain both locations",allLocations.contains(location2));


    }
}
