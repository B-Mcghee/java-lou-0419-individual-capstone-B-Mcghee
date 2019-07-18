package com.tsg.superherosighting.dao;

import com.tsg.superherosighting.dto.SuperHero;
import com.tsg.superherosighting.dto.SuperPower;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSuperPower {
    @Autowired
    SuperHeroDaoImpl heroDao;

    private SuperHero[] testSuperHeroes = {
            new SuperHero(1, "SuperMan", "S on his chest", false, new ArrayList<>()),
            new SuperHero(2, "Batman", "like a bat", false, new ArrayList<>()),
            new SuperHero(3, "Wolverine", "metal claws", true, new ArrayList<>())
    };

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

    SuperHero superhero = testSuperHeroes[0];
    SuperHero superhero2 = testSuperHeroes[1];
    SuperHero superhero3 = testSuperHeroes[2];

    @Before
    public void setUp() {
        JdbcTemplate heySQL = heroDao.getHeySQL();
        heySQL.execute("DELETE FROM members WHERE id > 0");
        heySQL.execute("DELETE FROM herosightings WHERE id > 0");

        heySQL.execute("DELETE FROM heropowers WHERE id > 0");
        heySQL.execute("DELETE FROM sightings WHERE id > 0");
        heySQL.execute("DELETE FROM superpowers WHERE id > 0");
        heySQL.execute("DELETE FROM superheroes WHERE id > 0");
        heySQL.execute("DELETE FROM Organization WHERE id > 0");

        heySQL.execute("DELETE FROM Locations WHERE id > 0");

        String ADD_SUPERHERO = "INSERT INTO superheroes(id, name, description, isvillian)" +
                "VALUES(?,?,?,?)";
        for (SuperHero superHero : testSuperHeroes) {
            heySQL.update(ADD_SUPERHERO, superHero.getId(), superHero.getName(), superHero.getDescription(), superHero.isVillian());
        }

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

    }

    @Test
    public void testAddAndGetSuperPower() {

        SuperPower  power = testSuperPowers[0];

        heroDao.addSuperPower(power);
        List<SuperPower> allSuperPowers = heroDao.getAllSuperPower();

        assertNotNull("List should never be null", allSuperPowers);
        assertEquals("should only have 12  powers in list",12 , allSuperPowers.size());
        SuperPower testpower= heroDao.getSuperPower(power.getId());
        assertEquals("should equal each other", power,testpower );

    }

    @Test
    public void testGetAndUpdateSuperPower() {


        SuperPower power = testSuperPowers[1];



        List<SuperPower> allPowers = heroDao.getAllSuperPower();
        assertTrue("List should contain superpower", allPowers.contains(power));

        SuperPower toEdit = heroDao.getSuperPower(power.getId());
        assertEquals("they should equal each other", power, toEdit);
        //get organization out and edit it


        power.setType("breathe under water");
        assertNotEquals("power should not equal each other after changes", power, toEdit);
        heroDao.updateSuperPower(power);

        assertNotEquals(power, toEdit);

        toEdit = heroDao.getSuperPower(power.getId());

        assertEquals("after updating power they should equal each other", power, toEdit);
    }


    @Test
    public void testRemoveSuperhero() {
        SuperPower power = testSuperPowers[1];
        SuperPower power2 = testSuperPowers[0];

        List<SuperPower> allSuperPowers = heroDao.getAllSuperPower();
        assertTrue("list should be contain both powers",allSuperPowers.contains(power));
        assertTrue("list should be contain both superpowers",allSuperPowers.contains(power2));

        SuperPower fromDao = heroDao.getSuperPower(power.getId());

        assertEquals("both objects should equal each other",power, fromDao);

        heroDao.removeSuperPower(power.getId());
        allSuperPowers = heroDao.getAllSuperPower();

        assertEquals("list should be contain only power2",10,allSuperPowers.size());
        assertTrue("list should be contain both powers",allSuperPowers.contains(power2));

    }
}