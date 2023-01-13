/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.controller;

import ie.philb.fuelservice.domain.Delivery;
import ie.philb.fuelservice.service.DeliveryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PBradley
 */
@RestController
@RequestMapping(path = "/deliveries")
public class DeliveriesController {

    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Delivery> getDeliverys(@RequestParam(required = true) int tankId) {
        return deliveryService.findByTank(tankId);
    }
}