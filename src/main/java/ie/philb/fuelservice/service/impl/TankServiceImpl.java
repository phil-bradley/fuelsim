/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service.impl;

import ie.philb.fuelservice.domain.Tank;
import ie.philb.fuelservice.repository.TankRepository;
import ie.philb.fuelservice.service.TankService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PBradley
 */
@Service
public class TankServiceImpl implements TankService {

    private final TankRepository tankRepository;

    public TankServiceImpl(@Autowired TankRepository tankRepository) {
        this.tankRepository = tankRepository;
    }

    @Override
    public List<Tank> getTanks() {
        return tankRepository.findAll();
    }

    @Override
    public void save(Tank tank) {
        tank.setLastUpdated(new Date());
        tankRepository.save(tank);
    }
    
    @Override
    public Tank getById(int tankId) {
        return tankRepository.findById(tankId).get();
    }
}
