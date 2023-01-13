/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.repository;

import ie.philb.fuelservice.domain.Tank;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PBradley
 */
public interface TankRepository extends JpaRepository<Tank, Integer> {

}
