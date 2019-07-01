package com.tsg.superherosighting.dao;

import com.tsg.superherosighting.dto.Location;
import com.tsg.superherosighting.dto.Organization;
import com.tsg.superherosighting.dto.SuperHero;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
        heySQL.update(INSERT_MEMBERS, 14, 1, 2);
        heySQL.update(INSERT_MEMBERS, 15, 2, 2);
        heySQL.update(INSERT_MEMBERS, 16, 3, 3);

        testOrganizations[1].addMember(testSuperHeroes[0]);
        testOrganizations[1].addMember(testSuperHeroes[1]);
        testOrganizations[2].addMember(testSuperHeroes[2]);

    }

    @Test
    public void testAddOrganization() {
        Organization organization = new Organization(1,"Marvel", "New York",location1,new ArrayList<>());

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
