/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service.impl;

import ie.philb.fuelservice.domain.FuelingConfiguration;
import ie.philb.fuelservice.service.GradeService;
import ie.philb.fuelservice.service.PumpService;
import ie.philb.fuelservice.service.TankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ie.philb.fuelservice.service.FuelingConfigurationService;
import javax.annotation.PostConstruct;

/**
 *
 * @author PBradley
 */
@Service
public class FuelingConfigurationServiceImpl implements FuelingConfigurationService {

    @Autowired
    private PumpService pumpService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private TankService tankService;
    
    @Autowired
    private FuelingConfiguration fuelingConfiguration;

    @PostConstruct
    private void initFuelingConfigState() {
        updateConfiguration(fuelingConfiguration);
    }
    
    @Override
    public FuelingConfiguration updateConfiguration(FuelingConfiguration fuelingConfiguration) {
        fuelingConfiguration.update(pumpService.getPumps(), tankService.getTanks(), gradeService.getGrades());
        return fuelingConfiguration;
    }

}
