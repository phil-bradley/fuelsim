/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service;

import ie.philb.fuelservice.domain.Delivery;
import java.util.List;

/**
 *
 * @author PBradley
 */
public interface DeliveryService {

    Delivery getDeliveryById(int id);

    Delivery save(Delivery delivery);

    List<Delivery> findByTank(int tankId);

}
