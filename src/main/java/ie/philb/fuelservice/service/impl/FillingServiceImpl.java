/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service.impl;

import ie.philb.fuelservice.domain.Filling;
import ie.philb.fuelservice.domain.FillingState;
import ie.philb.fuelservice.domain.Pump;
import ie.philb.fuelservice.domain.Tank;
import ie.philb.fuelservice.repository.FillingRepository;
import ie.philb.fuelservice.repository.TankRepository;
import ie.philb.fuelservice.service.FillingService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PBradley
 */
@Service
public class FillingServiceImpl implements FillingService {

    private static final Logger logger = LoggerFactory.getLogger(FillingServiceImpl.class);
    
    private final FillingRepository fillingRepository;
    private final TankRepository tankRepository;

    public FillingServiceImpl(
            @Autowired FillingRepository fillingRepository,
            @Autowired TankRepository tankRepository) {
        this.fillingRepository = fillingRepository;
        this.tankRepository = tankRepository;
    }

    @Override
    public Filling save(Filling filling) {
        fillingRepository.save(filling);
        return fillingRepository.findById(filling.getId()).get();
    }

    @Override
    public Filling createFilling(Pump pump, Tank tank) {

        Filling filling = new Filling();
        filling.setFillingStatus(FillingState.INPROGRESS);
        filling.setPump(pump);
        filling.setStarted(new Date());
        filling.setVolumeMilliLitres(0);
        filling.setTank(tank);

        return save(filling);
    }

    @Override
    public Filling completeFilling(Filling filling) {

        filling.setFillingStatus(FillingState.PAYABLE);
        filling.setCompleted(new Date());
        fillingRepository.save(filling);

        int fillingVolumeLitres = (filling.getVolumeMilliLitres() / 1000);
        int tankId = filling.getTank().getId();
        
        logger.info("Attempting to fetch tank {}", tankId);
        
        Tank tank = tankRepository.findById(tankId).get();
        int newVolume = tank.getVolumeLitres() - fillingVolumeLitres;
        
        logger.info("Setting volume {} on tank {}", newVolume, tank);
        tank.setVolumeLitres(newVolume);
        tankRepository.save(tank);

        return filling;
    }

    @Override
    public Filling clearFilling(Filling filling) {

        filling.setFillingStatus(FillingState.PAID);
        filling.setPaid(new Date());
        fillingRepository.save(filling);

        return filling;
    }

    @Override
    public Filling getById(int id) {
        return fillingRepository.findById(id).get();
    }

}
