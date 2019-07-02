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
public class TestSuperHero {

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
    private Sighting[] testSightings = {
            new Sighting(1, location1, LocalDateTime.now().withNano(0), new ArrayList<>()),
            new Sighting(2, location2, LocalDateTime.now().minusDays(100).withNano(0), new ArrayList<>()),
            new Sighting(3, location1, LocalDateTime.now().plusDays(100).withNano(0), new ArrayList<>()),
            new Sighting(4, location3, LocalDateTime.now().plusDays(15).withNano(0), new ArrayList<>())
    };

    private SuperPower[] testSuperPowers ={
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


        /**
         *    __   _    __   _  _____ __  _   _  __
         *   / / ,' \ ,'_/ .' \/_  _// /,' \ / |/ /
         *  / /_/ o |/ /_ / o / / / / // o |/ || /
         * /___/|_,' |__//_n_/ /_/ /_/ |_,'/_/|_/
         */
        String ADD_LOCATION = "INSERT INTO Locations(id, name, description, address, latitude, longitude)" +
                "VALUES(?,?,?,?,?,?)";
        for (Location aLocation: testLocations){
            heySQL.update(ADD_LOCATION, aLocation.getId(), aLocation.getName(), aLocation.getDescription(), aLocation.getAddress(),
                    aLocation.getLatitude(), aLocation.getLongitude());
        }
        /**
         *    ____  ____  _________    _   ___________   ___  ______________  _   __
         *   / __ \/ __ \/ ____/   |  / | / /  _/__  /  /   |/_  __/  _/ __ \/ | / /
         *  / / / / /_/ / / __/ /| | /  |/ // /   / /  / /| | / /  / // / / /  |/ /
         * / /_/ / _, _/ /_/ / ___ |/ /|  // /   / /__/ ___ |/ / _/ // /_/ / /|  /
         * \____/_/ |_|\____/_/  |_/_/ |_/___/  /____/_/  |_/_/ /___/\____/_/ |_/
         */

        String ADD_ORGANIZATION = "INSERT INTO organization(id, name, description, locationid)" +
                "VALUES(?,?,?,?)";

        for (Organization aOrganization: testOrganizations){
            heySQL.update(ADD_ORGANIZATION, aOrganization.getId(), aOrganization.getName(), aOrganization.getDescription(), aOrganization.getLocation().getId());
        }
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
        for (SuperHero superHero: testSuperHeroes){
            heySQL.update(ADD_SUPERHERO, superHero.getId(), superHero.getName(), superHero.getDescription(), superHero.isVillian());
        }
        String INSERT_INTO_SUPERPOWERS = "INSERT INTO superpowers(id, type) VALUES(?,?)";
        for (SuperPower powers: testSuperPowers){
            heySQL.update(INSERT_INTO_SUPERPOWERS,powers.getId(), powers.getType());
        }

        /**
         *    _____ ____________  _____________   _____________
         *   / ___//  _/ ____/ / / /_  __/  _/ | / / ____/ ___/
         *   \__ \ / // / __/ /_/ / / /  / //  |/ / / __ \__ \
         *  ___/ // // /_/ / __  / / / _/ // /|  / /_/ /___/ /
         * /____/___/\____/_/ /_/ /_/ /___/_/ |_/\____//____/
         *
         */

        String INSERT_INTO_SIGHTINGS = "INSERT INTO sightings(id, locationid, date) VALUES(?,?,?)";
        for (Sighting aSighting: testSightings){
            heySQL.update(INSERT_INTO_SIGHTINGS,aSighting.getId(), aSighting.getLocation().getId(),aSighting.getDate());
        }
        testOrganizations[1].addMember(testSuperHeroes[0]);
        testOrganizations[1].addMember(testSuperHeroes[1]);
        testOrganizations[2].addMember(testSuperHeroes[2]);
        Sighting sighting = testSightings[0];
        Sighting sighting2 = testSightings[1];
        Sighting sighting3 = testSightings[2];
        Sighting sighting4 = testSightings[3];

        sighting.addSighting(superhero);
        sighting.addSighting(superhero3);
        sighting2.addSighting(superhero2);
        sighting2.addSighting(superhero3);
        sighting3.addSighting(superhero);
        sighting3.addSighting(superhero2);
        sighting4.addSighting(superhero);
        sighting4.addSighting(superhero3);
        sighting4.addSighting(superhero2);



        String INSERT_MEMBERS = "INSERT INTO members(id, heroid, organizationid) VALUES(?,?,?)";
        heySQL.update(INSERT_MEMBERS, 14, 1, 2);
        heySQL.update(INSERT_MEMBERS, 15, 2, 2);
        heySQL.update(INSERT_MEMBERS, 16, 3, 3);

        testOrganizations[1].addMember(testSuperHeroes[0]);
        testOrganizations[1].addMember(testSuperHeroes[1]);
        testOrganizations[2].addMember(testSuperHeroes[2]);




        String INSERT_HEROSIGHTINGS = "INSERT INTO heroSightings(id, heroid, sightingId) VALUES(?,?,?)";
        heySQL.update(INSERT_HEROSIGHTINGS, 14, 1, 1);
        heySQL.update(INSERT_HEROSIGHTINGS, 15, 3, 1);
        heySQL.update(INSERT_HEROSIGHTINGS, 16, 2, 2);
        heySQL.update(INSERT_HEROSIGHTINGS, 17, 3, 2);
        heySQL.update(INSERT_HEROSIGHTINGS, 18, 1, 3);
        heySQL.update(INSERT_HEROSIGHTINGS, 19, 2, 3);
        heySQL.update(INSERT_HEROSIGHTINGS, 20, 1, 4);
        heySQL.update(INSERT_HEROSIGHTINGS, 21, 2, 4);
        heySQL.update(INSERT_HEROSIGHTINGS, 22, 3, 4);

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




    }

    @Test
    public void testAddSuperhero() {

        SuperHero  superHero = superhero;

        heroDao.addSuperHero(superHero);
        List<SuperHero> allSuperHeros = heroDao.getAllSuperHeros();

        assertNotNull("List should never be null", allSuperHeros);
        assertEquals("should only have 4 organization in list",4 , allSuperHeros.size());
        SuperHero testHero= heroDao.getSuperHero(superHero.getId());
        assertEquals("should equal each other", superHero, testHero);

    }

    @Test
    public void testUpdatHero() {


        SuperHero hero= superhero;
        SuperHero withMembers = heroDao.addSuperHero(hero);


        List<SuperHero> allHeroes = heroDao.getAllSuperHeros();
        assertTrue("List should contain hero", allHeroes.contains(hero));

        SuperHero toEdit = heroDao.getSuperHero(hero.getId());
        assertEquals("they should equal each other", hero, toEdit);
        //get organization out and edit it


        hero.setName("spider man");
        hero.setDescription("a part of marvel");
        assertNotEquals("hero should not equal each other after changes", hero, toEdit);
        heroDao.updateSuperHero(hero);

        assertNotEquals(hero, toEdit);

        toEdit = heroDao.getSuperHero(hero.getId());

        assertEquals("after updating organization they should equal each other", hero, toEdit);
    }


    @Test
    public void testRemoveSuperhero() {
        SuperHero hero= superhero;
        SuperHero hero2 = superhero2;
        heroDao.addSuperHero(hero);
        heroDao.addSuperHero(hero2);

        List<SuperHero> allSuperheroes = heroDao.getAllSuperHeros();
        assertTrue("list should be contain both organizations",allSuperheroes.contains(hero));
        assertTrue("list should be contain both organizations",allSuperheroes.contains(hero2));

        SuperHero fromDao = heroDao.getSuperHero(hero.getId());

        assertEquals("both objects should equal each other",hero, fromDao);

        heroDao.removeSuperHero(hero.getId());
        allSuperheroes = heroDao.getAllSuperHeros();

        assertEquals("list should be contain only organization2",4,allSuperheroes.size());
        assertTrue("list should be contain both organizations",allSuperheroes.contains(hero2));

    }
}
