/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.repository;

import ie.philb.fuelservice.domain.Delivery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author PBradley
 */
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    List<Delivery> findByTank(int tankId);

}
