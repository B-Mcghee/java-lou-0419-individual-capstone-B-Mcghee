package com.tsg.superherosighting.ops;

import com.tsg.superherosighting.dao.SuperHeroDaoImpl;
import com.tsg.superherosighting.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/HeroSighting")
public class SuperHeroController {
    @Autowired
    SuperHeroDaoImpl heroDao;

    Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();



    @GetMapping("/home")
    public String landingPage(Model model){
        List<Sighting> sightings = heroDao.numOfSightings(10);

        model.addAttribute("sightings", sightings);

        return "home";
    }
    @GetMapping("/superpower/display")
    public String viewAllPowers(Model model){
        List<SuperPower> listOfPowers = heroDao.getAllSuperPower();




        model.addAttribute("allPowers", listOfPowers);

        return "superpower";
    }


    @PostMapping("/superpower/display")
    public String createNewPower( HttpServletRequest request){
        SuperPower power = new SuperPower();
        String description = request.getParameter("description");
        power.setType(description);
        heroDao.addSuperPower(power);

        return "redirect:/HeroSighting/superpower/display";
    }

    @GetMapping("/superpower/get/id/{id}")@ResponseBody
    public List<SuperHero> getHeroPower(@PathVariable int id){

        SuperPower superPower = heroDao.getSuperPower(id);

        List<SuperHero> heroesWithPowers = heroDao.getSuperHeroWithSpecificPower(id);



        return heroesWithPowers;
    }

    @GetMapping("/superpower/display/id/{id}")
    public String viewOnePower(Model model, @PathVariable int id){
        List<SuperPower> allPowers = heroDao.getAllSuperPower();

        SuperPower superPower = heroDao.getSuperPower(id);
        List<SuperHero> heroesWithPowers = heroDao.getSuperHeroWithSpecificPower(id);
        model.addAttribute("singlepower", superPower.getType());
        model.addAttribute("allPowers", allPowers);
        model.addAttribute("heroesWithPower",heroesWithPowers );



        return "superpower";
    }



    @GetMapping("/editsuperpower/update")
    public String updatePower(Model model, HttpServletRequest request){
        List<SuperPower> allPowers = heroDao.getAllSuperPower();
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower power = heroDao.getSuperPower(id);

        model.addAttribute("power", power);
        model.addAttribute("allPowers", allPowers);

        return "editsuperpower";
    }



    @PostMapping("/editsuperpower/update")
    public String editPower( HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        SuperPower power = new SuperPower(id, type);

        heroDao.updateSuperPower(power);

        return "redirect:/HeroSighting/superpower/display";
    }


    @GetMapping("/superpower/delete")
    public String deletePower(Integer id, Model model) {
        heroDao.removeSuperPower(id);
        List<SuperPower> allPowers = heroDao.getAllSuperPower();


        model.addAttribute("allPowers", allPowers);

        return "redirect:/HeroSighting/superpower/display";
    }



/**
 *
 *
 *
 *  ____  _   _ ____  _____ ____    _   _ _____ ____   ___
 * / ___|| | | |  _ \| ____|  _ \  | | | | ____|  _ \ / _ \
 * \___ \| | | | |_) |  _| | |_) | | |_| |  _| | |_) | | | |
 *  ___) | |_| |  __/| |___|  _ <  |  _  | |___|  _ <| |_| |
 * |____/ \___/|_|   |_____|_| \_\ |_| |_|_____|_| \_\\___/
 *
 *
 */

@GetMapping("/superhero/display")
public String viewAllSuperHeroes(Model model){
    List<SuperPower> allPowers = heroDao.getAllSuperPower();
    model.addAttribute("allPowers", allPowers);

    List<SuperHero> allHeroes = heroDao.getAllSuperHeros();

    model.addAttribute("allHeroes", allHeroes);

    return "superhero";
}

    @PostMapping("/superhero/display")
    public String addHero(HttpServletRequest request){
        SuperHero hero = new SuperHero();
        String description = request.getParameter("herodescription");
        String name = request.getParameter("name");
        String isVillain = request.getParameter("villain");
        String superHero = request.getParameter("hero");
        if (!Boolean.parseBoolean(superHero)){
            hero.setVillian(false);
        }

        String[] powers = request.getParameterValues("superpower");
        List<SuperPower> retrievedPowers = new ArrayList<>();
        for (String i: powers){
            retrievedPowers.add(heroDao.getSuperPower(Integer.parseInt(i)));
        }
        hero.setDescription(description);
        hero.setName(name);
        hero.setHeroPowers(retrievedPowers);
        heroDao.addSuperHero(hero);

        return "redirect:/HeroSighting/superhero/display";
    }
    @PostMapping("/editsuperhero/update")
    public String editHero( HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Boolean villain = Boolean.parseBoolean(request.getParameter("villain"));
        String[] powers = request.getParameterValues("superpower");
        List<SuperPower> superPowers = new ArrayList<>();
        for (String i: powers){
            superPowers.add(heroDao.getSuperPower(Integer.parseInt(i)));
        }
        SuperHero hero = new SuperHero(id, name, description, villain, superPowers);

        heroDao.updateSuperHero(hero);

        return "redirect:/HeroSighting/superhero/display";
    }

    @GetMapping("/editsuperhero/update")
    public String updatehero(Model model, Integer id){

        SuperHero hero = heroDao.getSuperHero(id);

        List<SuperHero> heroes = heroDao.getAllSuperHeros();
        List<SuperPower> powers = heroDao.getAllSuperPower();
        model.addAttribute("powers", powers);

        model.addAttribute("hero", hero);
        model.addAttribute("heroes", heroes);

        return "editsuperhero";
    }

    @GetMapping("/superhero/delete")
    public String deleteHero(Integer id) {
        heroDao.removeSuperHero(id);


        return "redirect:/HeroSighting/superhero/display";
    }
//    @PostMapping("/editsuperhero/update")
//    public String edithero( HttpServletRequest request){
//        int id = Integer.parseInt(request.getParameter("id"));
//        String description = request.getParameter("description");
//        String villain = request.getParameter("hero");
//
//        SuperPower power = new SuperPower(id, type);
//
//        heroDao.updateSuperPower(power);
//
//        return "redirect:/HeroSighting/superpower/display";
//    }

//    @GetMapping
//    public String viewAllSuperHeroes(@RequestParam(value ="heroDescription"),String heroDescription, @RequestParam(value = "name"), String name ){
////        String name = request.getParameter("name");
////        String heroDescription = request.getParameter("heroDescription");
////        String isVillain = request.getParameter("villain");
////        String superHero = request.getParameter("hero");
////        String id = request.getParameter("superPoweId");
//
//
//        return "superhero" + name + " " + heroDescription ;
//    }

    /**
     *
     *   ___  _ __ __ _  __ _ _ __ (_)______ _| |_(_) ___  _ __
     *  / _ \| '__/ _` |/ _` | '_ \| |_  / _` | __| |/ _ \| '_ \
     * | (_) | | | (_| | (_| | | | | |/ / (_| | |_| | (_) | | | |
     *  \___/|_|  \__, |\__,_|_| |_|_/___\__,_|\__|_|\___/|_| |_|
     *            |___/
     */

    @GetMapping("/editorganization/update")
    public String editorganization(Model model, Integer id){

        Organization organization = heroDao.getSingleOrganization(id);
        model.addAttribute("organization",organization);

        List<SuperHero> members = heroDao.getAllSuperHeros();
        model.addAttribute("members", members);

        List<Location> locations = heroDao.getAllLocations();
        model.addAttribute("locations", locations);

        List<Organization> organizations = heroDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);

        return "editorganization";
    }

    @GetMapping("/organization/display")
    public String organizationMain(Model model){
        List<SuperHero> members = heroDao.getAllSuperHeros();

        model.addAttribute("members", members);

        List<Location> locations = heroDao.getAllLocations();
        model.addAttribute("locations", locations);
        List<Organization> organizations = heroDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organization";


    }

    @GetMapping("/organization/delete")
    public String deleteOrganization(Model model, Integer id){
        heroDao.removeOrganization(id);
        return "redirect:/HeroSighting/organization/display";
    }

    @PostMapping("/organization/display")
    public String addOrganization(HttpServletRequest request){

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String locationid = request.getParameter("location");
        Location location = heroDao.getLocation(Integer.parseInt(locationid));
        String[] members = request.getParameterValues("member");


        List<SuperHero> heroes = new ArrayList<>();
        for (String i: members){
            heroes.add(heroDao.getSuperHero(Integer.parseInt(i)));
        }


        Organization organization = new Organization( );
        organization.setName(name);
        organization.setDescription(description);
        organization.setMembers(heroes);
        organization.setLocation(location);
        heroDao.addOrganization(organization);

        return "redirect:/HeroSighting/organization/display";
    }
//
    @PostMapping("/editorganization/update")
    public String updateOrganization( HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id" ));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String locationid = request.getParameter("location");
        Location location = heroDao.getLocation(Integer.parseInt(locationid));
        String[] members = request.getParameterValues("member");


        List<SuperHero> heroes = new ArrayList<>();
        for (String i: members){
            heroes.add(heroDao.getSuperHero(Integer.parseInt(i)));
        }


        Organization organization = new Organization(id, name, description, location,heroes );

        heroDao.updateOrganization(organization);

        return "redirect:/HeroSighting/organization/display";
    }

    /**
     *  _                    _   _
     * | |    ___   ___ __ _| |_(_) ___  _ __
     * | |   / _ \ / __/ _` | __| |/ _ \| '_ \
     * | |__| (_) | (_| (_| | |_| | (_) | | | |
     * |_____\___/ \___\__,_|\__|_|\___/|_| |_|
     */

    @GetMapping("/location/display")
    public String locationMainPage(Model model){
        List<Location> locations = heroDao.getAllLocations();
        model.addAttribute("locations", locations);


        return "location";
    }

    @GetMapping("/editlocation/update")
    public String editlocation(Model model, Integer id){

        Location location = heroDao.getLocation(id);
        model.addAttribute("location",location);

        List<Location> locations = heroDao.getAllLocations();
        model.addAttribute("locations", locations);


        return "editlocation";
    }



    @PostMapping("/editlocation/update")
    public String updateLocation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id" ));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        BigDecimal latitude = new BigDecimal(request.getParameter("latitude"));
        BigDecimal longitude = new BigDecimal(request.getParameter("longitude"));



        Location location = new Location(id, name, description,address, latitude,longitude );
        heroDao.updateLocation(location);

        return "redirect:/HeroSighting/location/display";
    }

    @PostMapping("/location/display")
    public String addLocation(HttpServletRequest request, BindingResult result){

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");

        BigDecimal latitude;
        BigDecimal longitude;
        try{
        latitude = new BigDecimal(request.getParameter("latitude"));
         longitude = new BigDecimal(request.getParameter("longitude"));
        }catch (NumberFormatException e){
            return "redirect:/HeroSighting/location/display";
        }

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        locationViolations = validate.validate(location);

        if(result.hasErrors()) {
            return "location/display";
        }
        if(locationViolations.isEmpty()) {
            heroDao.addLocation(location);
        }


        return "redirect:/HeroSighting/location/display";
    }

    @GetMapping("/location/delete")
    public String deleteLocation(Model model, Integer id){
        heroDao.removeLocation(id);

        return "redirect:/HeroSighting/location/display";
    }



    /**
     *      _       _     _   _
     *  ___(_) __ _| |__ | |_(_)_ __   __ _
     * / __| |/ _` | '_ \| __| | '_ \ / _` |
     * \__ \ | (_| | | | | |_| | | | | (_| |
     * |___/_|\__, |_| |_|\__|_|_| |_|\__, |
     *        |___/                   |___/
     */


    @GetMapping("/editsighting/update")
    public String editSighting(Model model, Integer id) throws ParseException {
        Sighting sighting = heroDao.getSingleSighting(id);
        model.addAttribute("sighting", sighting);



        List<Sighting> sightings = heroDao.getAllSightings();
        model.addAttribute("sightings", sightings);

        List<SuperHero> members = heroDao.getAllSuperHeros();
        model.addAttribute("members", members);

        List<Location> locations = heroDao.getAllLocations();
        model.addAttribute("locations", locations);

        List<Organization> organizations = heroDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);

        return "editsighting";
    }

    @GetMapping("/sighting/display")
    public String sightingMainPage(Model model){
        List<SuperHero> members = heroDao.getAllSuperHeros();
        model.addAttribute("members", members);

        List<Location> locations = heroDao.getAllLocations();
        model.addAttribute("locations", locations);

        List<Sighting> sightings = heroDao.getAllSightings();

        model.addAttribute("sightings", sightings);



        Sighting sighting = new Sighting();
        model.addAttribute("sight", sighting);
        return "sighting";


    }

    @GetMapping("/sighting/delete")
    public String deleteSighting(Model model, Integer id){
        heroDao.removeSighting(id);

        return "redirect:/HeroSighting/sighting/display";
    }

    @PostMapping("/sighting/display")
    public String addSighting(HttpServletRequest request){
        String date = request.getParameter("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date,formatter);
        String locationid = request.getParameter("location");
        Location location = heroDao.getLocation(Integer.parseInt(locationid));
        String[] members = request.getParameterValues("heroes");


        List<SuperHero> heroes = new ArrayList<>();
        for (String i: members){
            heroes.add(heroDao.getSuperHero(Integer.parseInt(i)));
        }


        Sighting sighting = new Sighting();


        sighting.setHeroes(heroes);
        sighting.setLocation(location);
        sighting.setDate(dateTime);
        heroDao.addSighting(sighting);


        return "redirect:/HeroSighting/sighting/display";
    }

    @PostMapping("/editsighting/update")
    public String updatesighting( HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        String date = request.getParameter("datetime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date,formatter);
        String locationid = request.getParameter("location");
        Location location = heroDao.getLocation(Integer.parseInt(locationid));
        String[] members = request.getParameterValues("member");


        List<SuperHero> heroes = new ArrayList<>();
        for (String i: members){
            heroes.add(heroDao.getSuperHero(Integer.parseInt(i)));
        }


        Sighting sighting = new Sighting(id, location, dateTime, heroes);

        heroDao.updateSighting(sighting);

        return "redirect:/HeroSighting/sighting/display";
    }

}
