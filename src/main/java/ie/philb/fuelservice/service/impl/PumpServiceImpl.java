/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service.impl;

import ie.philb.fuelservice.domain.Pump;
import ie.philb.fuelservice.repository.PumpRepository;
import ie.philb.fuelservice.service.PumpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PBradley
 */
@Service
public class PumpServiceImpl implements PumpService {

    private final PumpRepository pumpRepository;

    public PumpServiceImpl(@Autowired PumpRepository pumpRepository) {
        this.pumpRepository = pumpRepository;
    }

    @Override
    public List<Pump> getPumps() {
        return pumpRepository.findAll();
    }

}
