/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.repository;

import ie.philb.fuelservice.domain.Pump;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PBradley
 */
public interface PumpRepository extends JpaRepository<Pump, Integer> {

}