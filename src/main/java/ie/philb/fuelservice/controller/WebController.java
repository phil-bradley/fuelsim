/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.controller;

import ie.philb.fuelservice.domain.FuelingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author PBradley
 */
@Controller
public class WebController {

    @Autowired
    private FuelingConfiguration fuelingConfiguration;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/grades")
    public String grades(Model model) {
        model.addAttribute("fuelingConfiguration", fuelingConfiguration);
        return "grades";
    }

    @GetMapping("/websocket")
    public String websocket(Model model) {
        model.addAttribute("fuelingConfiguration", fuelingConfiguration);
        return "websocket";
    }
}
