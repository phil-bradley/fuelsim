/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service;

import ie.philb.fuelservice.domain.Filling;
import ie.philb.fuelservice.domain.Pump;
import ie.philb.fuelservice.domain.Tank;

/**
 *
 * @author PBradley
 */
public interface FillingService {

    Filling getById(int id);

    Filling save(Filling filling);

    Filling createFilling(Pump pump, Tank tank);

    Filling completeFilling(Filling filling);

    Filling clearFilling(Filling filling);
}
