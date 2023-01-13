/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service.impl;

import ie.philb.fuelservice.domain.Delivery;
import ie.philb.fuelservice.repository.DeliveryRepository;
import ie.philb.fuelservice.service.DeliveryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PBradley
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(@Autowired DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Delivery getDeliveryById(int id) {
        return deliveryRepository.findById(id).get();
    }

    @Override
    public Delivery save(Delivery delivery) {
        deliveryRepository.save(delivery);
        return deliveryRepository.findById(delivery.getId()).get();
    }

    @Override
    public List<Delivery> findByTank(int tankId) {
        return deliveryRepository.findByTank(tankId);
    }
}
