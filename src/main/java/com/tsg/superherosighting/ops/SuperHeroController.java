package com.tsg.superherosighting.ops;

import com.tsg.superherosighting.dao.SuperHeroDaoImpl;
import com.tsg.superherosighting.dto.SuperHero;
import com.tsg.superherosighting.dto.SuperPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/HeroSighting")
public class SuperHeroController {
    @Autowired
    SuperHeroDaoImpl heroDao;

    Map<Integer, SuperPower> allSuperPowers = new HashMap<>();
    @GetMapping()
    public String mainPage(Model model){

        model.addAttribute("greeting", "Have you seen a Super Hero??");

        return "HeroSighting";
    }

    @GetMapping("/superpower/display")
    public String viewAllPowers(Model model){
        List<SuperPower> listOfPowers = new ArrayList<>(allSuperPowers.values());
        for (SuperPower i: heroDao.getAllSuperPower()) {
            allSuperPowers.put(i.getId(), i);
        }
            model.addAttribute("allPowers", new ArrayList<>(allSuperPowers.values()));

        return "superpower";
    }

    @GetMapping("/superpower/display/id/{id}")
    public String viewOnePower(Model model, @PathVariable int id){
        for (SuperPower i: heroDao.getAllSuperPower()) {
            allSuperPowers.put(i.getId(), i);
        }

        SuperPower superPower = heroDao.getSuperPower(id);
        List<SuperHero> heroesWithPowers = heroDao.getSuperHeroWithPower(id);
        model.addAttribute("singlepower", superPower.getType());
        model.addAttribute("allPowers", new ArrayList<>(allSuperPowers.values()));
        model.addAttribute("heroesWithPower",heroesWithPowers );



        return "superpower";
    }

}
