package com.tsg.superherosighting.dao;

import com.tsg.superherosighting.dto.*;
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

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestOrganizationImpl {

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
    Location location1 = testLocations[0];
    Location location2 = testLocations[1];
    Location location3 = testLocations[2];

    private SuperPower[] testSuperPowers = {
            new SuperPower(1, "fly"),
            new SuperPower(2, "laser beam eyes"),
            new SuperPower(3, "lift 1000x bodyweight"),
            new SuperPower(4, "wash all clothes, fold them and put them away instantly"),
            new SuperPower(5, "wash all dishes, dry them, and put them away instantly"),
            new SuperPower(6, "run as fast as the speed of light"),
            new SuperPower(7, "teleport"),
            new SuperPower(8, "shapeshifter"),
            new SuperPower(9, "control the weather"),
            new SuperPower(10, "freeze objects"),
            new SuperPower(11, "control fire")

    };
    private Sighting[] testSightings = {
            new Sighting(1, location1, LocalDateTime.now().withNano(0), new ArrayList<>()),
            new Sighting(2, location2, LocalDateTime.now().minusDays(100).withNano(0), new ArrayList<>()),
            new Sighting(3, location1, LocalDateTime.now().plusDays(100).withNano(0), new ArrayList<>()),
            new Sighting(4, location3, LocalDateTime.now().plusDays(15).withNano(0), new ArrayList<>())
    };

    private Organization[] testOrganizations = {
            new Organization(1,"Marvel", "New York",location1,new ArrayList<>()),
            new Organization(2,"Justice League", "New York",location2,new ArrayList<>()),
            new Organization(3,"X-Men", "Unknown",location3,new ArrayList<>())
    };

    private SuperHero[] testSuperHeroes = {
            new SuperHero(1,"SuperMan", "S on his chest",false,new ArrayList<>()),
            new SuperHero(2,"Batman", "like a bat",false,new ArrayList<>()),
            new SuperHero(3,"Wolverine", "metal claws",true,new ArrayList<>())
    };

    SuperHero superhero = testSuperHeroes[0];
    SuperHero superhero2 = testSuperHeroes[1];
    SuperHero superhero3 = testSuperHeroes[2];


    @Before
    public void setUp(){
        JdbcTemplate heySQL = heroDao.getHeySQL();
        heySQL.execute("DELETE FROM members WHERE id > 0");
        heySQL.execute("DELETE FROM herosightings WHERE id > 0");

        heySQL.execute("DELETE FROM heropowers WHERE id > 0");
        heySQL.execute("DELETE FROM sightings WHERE id > 0");
        heySQL.execute("DELETE FROM superpowers WHERE id > 0");
        heySQL.execute("DELETE FROM superheroes WHERE id > 0");
        heySQL.execute("DELETE FROM Organization WHERE id > 0");

        heySQL.execute("DELETE FROM Locations WHERE id > 0");



        String ADD_LOCATION = "INSERT INTO Locations(id, name, description, address, latitude, longitude)" +
                "VALUES(?,?,?,?,?,?)";
        for (Location aLocation: testLocations){
            heySQL.update(ADD_LOCATION, aLocation.getId(), aLocation.getName(), aLocation.getDescription(), aLocation.getAddress(),
                    aLocation.getLatitude(), aLocation.getLongitude());
        }

        String ADD_ORGANIZATION = "INSERT INTO organization(id, name, description, locationid)" +
                "VALUES(?,?,?,?)";

        for (Organization aOrganization: testOrganizations){
            heySQL.update(ADD_ORGANIZATION, aOrganization.getId(), aOrganization.getName(), aOrganization.getDescription(), aOrganization.getLocation().getId());
        }
        String ADD_SUPERHERO = "INSERT INTO superheroes(id, name, description, isvillian)" +
                "VALUES(?,?,?,?)";
        for (SuperHero superHero: testSuperHeroes){
            heySQL.update(ADD_SUPERHERO, superHero.getId(), superHero.getName(), superHero.getDescription(), superHero.isVillian());
        }
        String INSERT_MEMBERS = "INSERT INTO members(id, heroid, organizationid) VALUES(?,?,?)";
        heySQL.update(INSERT_MEMBERS, 1, 1, 2);
        heySQL.update(INSERT_MEMBERS, 2, 2, 2);
        heySQL.update(INSERT_MEMBERS, 3, 3, 3);
        String INSERT_INTO_SUPERPOWERS = "INSERT INTO superpowers(id, type) VALUES(?,?)";
        for (SuperPower powers: testSuperPowers){
            heySQL.update(INSERT_INTO_SUPERPOWERS,powers.getId(), powers.getType());
        }
        String INSERT_HEROPOWERS = "INSERT INTO heropowers(id, heroid,powersid ) VALUES(?,?,?)";
        heySQL.update(INSERT_HEROPOWERS,1,  1, 1);
        heySQL.update(INSERT_HEROPOWERS,2,  1, 2);
        heySQL.update(INSERT_HEROPOWERS, 3, 1, 3);
        heySQL.update(INSERT_HEROPOWERS, 4, 2, 4);
        heySQL.update(INSERT_HEROPOWERS, 5, 2, 5);
        heySQL.update(INSERT_HEROPOWERS,6,  2, 6);
        heySQL.update(INSERT_HEROPOWERS,  7,2, 7);
        heySQL.update(INSERT_HEROPOWERS,8, 3, 8);
        heySQL.update(INSERT_HEROPOWERS,9, 3, 9);
        heySQL.update(INSERT_HEROPOWERS, 10, 3, 10);

        for (int i = 0; i < 3; i++) {
            superhero.addPower(testSuperPowers[i]);
        }
        for (int i = 3; i < 7; i++) {
            superhero2.addPower(testSuperPowers[i]);
        }
        for (int i = 7; i < 10; i++) {
            superhero3.addPower(testSuperPowers[i]);
        }
        testOrganizations[1].addMember(testSuperHeroes[0]);
        testOrganizations[1].addMember(testSuperHeroes[1]);
        testOrganizations[2].addMember(testSuperHeroes[2]);

    }

    @Test
    public void testAddOrganization() {
        Organization organization = testOrganizations[1];

        heroDao.addOrganization(organization);
        List<Organization> allOrganizations = heroDao.getAllOrganizations();

        assertNotNull("List should never be null", allOrganizations);
        assertEquals("should only have 4 organization in list",4 , allOrganizations.size());

        Organization testOrganization = heroDao.getSingleOrganization(organization.getId());
        assertEquals("should equal each other", organization, testOrganization);
    }

    @Test
    public void testGetOrganization() {
        Organization organization = new Organization(1,"Marvel", "New York",location1,new ArrayList<>());
        heroDao.addOrganization(organization);

        Organization testOrganization = heroDao.getSingleOrganization(organization.getId());

        assertNotNull("List  should never be null", testOrganization);
        assertEquals("should equal each other", organization, testOrganization);
    }

    @Test
    public void testEditOrganization() {
        Organization organization = new Organization(1,"Marvel", "New York",location1,new ArrayList<>());
        Organization withMembers = heroDao.addOrganization(organization);


        List<Organization>allOrganizations = heroDao.getAllOrganizations();
        assertTrue("List should contain organization", allOrganizations.contains(organization));

        Organization toEdit = heroDao.getSingleOrganization(organization.getId());
        assertEquals("they should equal each other", organization, toEdit);
        //get organization out and edit it


        organization.setName("Agents of Shield");
        organization.setDescription("a part of marvel");
        assertNotEquals("Organizations should not equal each other after changes", organization, toEdit);
        heroDao.updateOrganization(organization);

        assertNotEquals(organization, toEdit);

        toEdit = heroDao.getSingleOrganization(organization.getId());

        assertEquals("after updating organization they should equal each other", organization, toEdit);



    }

    @Test
    public void testRemoveOrganization() {
        Organization organization = new Organization(1,"Marvel", "New York",location1,new ArrayList<>());
        heroDao.addOrganization(organization);
        Organization organization2 = new Organization(2,"Justice League", "New York",location2,new ArrayList<>());
        heroDao.addOrganization(organization2);

        List<Organization> allOrganizations = heroDao.getAllOrganizations();
        assertTrue("list should be contain both organizations",allOrganizations.contains(organization));
        assertTrue("list should be contain both organizations",allOrganizations.contains(organization2));

        Organization fromDao = heroDao.getSingleOrganization(organization.getId());

        assertEquals("both objects should equal each other",organization, fromDao);

        heroDao.removeOrganization(organization.getId());
        allOrganizations = heroDao.getAllOrganizations();

        assertEquals("list should be contain only organization2",4,allOrganizations.size());
        assertTrue("list should be contain both organizations",allOrganizations.contains(organization2));


    }
}
