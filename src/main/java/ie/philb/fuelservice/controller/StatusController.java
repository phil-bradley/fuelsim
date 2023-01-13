/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.controller;

import ie.philb.fuelservice.domain.Filling;
import ie.philb.fuelservice.domain.FuelingConfiguration;
import ie.philb.fuelservice.domain.Pump;
import ie.philb.fuelservice.domain.Tank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PBradley
 */
@RestController()
@RequestMapping(path = "/status")
public class StatusController {

    @Autowired
    private FuelingConfiguration fuelingConfiguration;

    @RequestMapping(method = RequestMethod.GET)
    public FuelingConfiguration getStatus() {
        return fuelingConfiguration;
    }

    @RequestMapping(path = "/pumps", method = RequestMethod.GET)
    public List<Pump> getPumps() {
        return fuelingConfiguration.getPumps();
    }

    @RequestMapping(path = "/fillings", method = RequestMethod.GET)
    public List<Filling> getFIllings() {
        return fuelingConfiguration.getFillings();
    }

    @RequestMapping(path = "/tanks", method = RequestMethod.GET)
    public List<Tank> getTanks() {
        return fuelingConfiguration.getTanks();
    }

}
