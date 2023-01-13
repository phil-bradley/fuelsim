/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service;

import ie.philb.fuelservice.domain.Tank;
import java.util.List;

/**
 *
 * @author PBradley
 */
public interface TankService {

    List<Tank> getTanks();

    void save(Tank tank);
    
    Tank getById(int id);
}
