//package com.tsg.superherosighting.ops;
//
//import com.tsg.superherosighting.dao.SuperHeroDaoImpl;
//import com.tsg.superherosighting.dto.SuperHero;
//import com.tsg.superherosighting.dto.SuperPower;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/HeroSighting")
//public class RestSuperHeroController {
//    @Autowired
//    SuperHeroDaoImpl heroDao;
//
//    Map<Integer, SuperPower> allSuperPowers = new HashMap<>();
//    Map<Integer, SuperHero> allSuperHeroes = new HashMap<>();
//    @GetMapping()
//    public String mainPage(Model model){
//
//        model.addAttribute("greeting", "Have you seen a Super Hero??");
//
//        return "HeroSighting";
//    }
//
//    @GetMapping("/superpower/display")
//    public String viewAllPowers(Model model){
//        List<SuperPower> listOfPowers = new ArrayList<>(allSuperPowers.values());
//        for (SuperPower i: heroDao.getAllSuperPower()) {
//            allSuperPowers.put(i.getId(), i);
//        }
//        model.addAttribute("allPowers", new ArrayList<>(allSuperPowers.values()));
//
//        return "superpower";
//    }
//
//    @PostMapping("/superpower/display")
//    public String createNewPower(@RequestBody SuperPower power, HttpServletRequest request ){
//        String description = request.getParameter("description");
//        power.setType(description);
//        heroDao.addSuperPower(power);
//
//        return "redirect:/superpower";
//    }
//
//    @GetMapping("/superpower/get/id/{id}")@ResponseBody
//    public List<SuperHero> getHeroPower(@PathVariable int id){
//
//        SuperPower superPower = heroDao.getSuperPower(id);
//
//        List<SuperHero> heroesWithPowers = heroDao.getSuperHeroWithSpecificPower(id);
//
//
//
//        return heroesWithPowers;
//    }
//
//    @GetMapping("/superpower/display/id/{id}")
//    public String viewOnePower(Model model, @PathVariable int id){
//        for (SuperPower i: heroDao.getAllSuperPower()) {
//            allSuperPowers.put(i.getId(), i);
//        }
//
//        SuperPower superPower = heroDao.getSuperPower(id);
//        List<SuperHero> heroesWithPowers = heroDao.getSuperHeroWithSpecificPower(id);
//        model.addAttribute("singlepower", superPower.getType());
//        model.addAttribute("allPowers", new ArrayList<>(allSuperPowers.values()));
//        model.addAttribute("heroesWithPower",heroesWithPowers );
//
//
//
//        return "superpower";
//    }
//
//    @GetMapping("/superpower/delete")
//    public String deletePower(Integer id){
//        heroDao.removeSuperPower(id);
//
//        return "redirect:/superpower/delete";
//    }
//
//    @PostMapping("/editsuperpower/update")
//    public String editPower(SuperPower power){
//        heroDao.updateSuperPower(power);
//
//        return "redirect:/editsuperpower/update";
//    }
//
//
//    /**
//     *
//     *
//     *
//     *  ____  _   _ ____  _____ ____    _   _ _____ ____   ___
//     * / ___|| | | |  _ \| ____|  _ \  | | | | ____|  _ \ / _ \
//     * \___ \| | | | |_) |  _| | |_) | | |_| |  _| | |_) | | | |
//     *  ___) | |_| |  __/| |___|  _ <  |  _  | |___|  _ <| |_| |
//     * |____/ \___/|_|   |_____|_| \_\ |_| |_|_____|_| \_\\___/
//     *
//     *
//     */
//
//    @GetMapping("/superhero/display")
//    public String viewAllSuperHeroes(Model model){
//        List<SuperPower> listOfPowers = new ArrayList<>(allSuperPowers.values());
//        for (SuperPower i: heroDao.getAllSuperPower()) {
//            allSuperPowers.put(i.getId(), i);
//        }
//        model.addAttribute("allPowers", new ArrayList<>(allSuperPowers.values()));
//
//        List<SuperHero> listOfHeroes = new ArrayList<>(allSuperHeroes.values());
//        for (SuperHero i: heroDao.getAllSuperHeros()) {
//            allSuperHeroes.put(i.getId(), i);
//        }
//        model.addAttribute("allHeroes", new ArrayList<>(allSuperHeroes.values()));
//
//        return "superhero";
//    }
//
//    @PostMapping("/superhero/POST")
//    public String addHero(@RequestBody SuperHero hero, HttpServletRequest request){
//        String description = request.getParameter("heroDescription");
//        String name = request.getParameter("name");
//        String isVillain = request.getParameter("villain");
//        String superHero = request.getParameter("hero");
//        if (Boolean.parseBoolean(superHero)){
//            hero.setVillian(false);
//        }
//
//        String[] powers = request.getParameterValues("superPowerId");
//        List<SuperPower> retrievedPowers = new ArrayList<>();
//        for (String i: powers){
//            retrievedPowers.add(heroDao.getSuperPower(Integer.parseInt(i)));
//        }
//        hero.setDescription(description);
//        hero.setName(name);
//        hero.setHeroPowers(retrievedPowers);
//        heroDao.addSuperHero(hero);
//
//        return "redirect:/superhero/display";
//    }
//
////    @GetMapping
////    public String viewAllSuperHeroes(@RequestParam(value ="heroDescription"),String heroDescription, @RequestParam(value = "name"), String name ){
//////        String name = request.getParameter("name");
//////        String heroDescription = request.getParameter("heroDescription");
//////        String isVillain = request.getParameter("villain");
//////        String superHero = request.getParameter("hero");
//////        String id = request.getParameter("superPoweId");
////
////
////        return "superhero" + name + " " + heroDescription ;
////    }
//}
