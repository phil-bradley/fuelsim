/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.philb.fuelservice.domain.Filling;
import ie.philb.fuelservice.domain.FuelingConfiguration;
import ie.philb.fuelservice.domain.Pump;
import ie.philb.fuelservice.domain.PumpState;
import ie.philb.fuelservice.domain.exception.FuelConfigurationException;
import ie.philb.fuelservice.domain.exception.NoSuchItemException;
import ie.philb.fuelservice.domain.exception.PumpStateException;
import ie.philb.fuelservice.service.FillingService;
import ie.philb.fuelservice.service.FuelingConfigurationService;
import ie.philb.fuelservice.util.RandomUtil;
import ie.philb.fuelservice.websocket.FillingsEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author PBradley
 */
@Component
public class FillingProgressManager {

    @Autowired
    private FuelingConfiguration fuelingConfiguration;

    @Autowired
    private FuelingConfigurationService fuelingConfigurationService;

    @Autowired
    private FillingService fillingService;

    @Autowired
    private ObjectMapper objectMapper;

    void updatePump(Pump pump) throws PumpStateException, NoSuchItemException, FuelConfigurationException, JsonProcessingException {
        Filling filling = fuelingConfiguration.getInProgressFilling(pump);

        fuelingConfiguration.updateInProgressFilling(pump);

        if (RandomUtil.succeedWithProbability(0.1) && filling.getVolumeMilliLitres() > 5000) {
            completePumpFilling(pump);
        } 
    }

    private void completePumpFilling(Pump pump) throws PumpStateException, NoSuchItemException, FuelConfigurationException, JsonProcessingException {
        Filling filling = fuelingConfiguration.getInProgressFilling(pump);
        pump.setPumpState(PumpState.IDLE);
        fillingService.completeFilling(filling);

        fuelingConfigurationService.updateConfiguration(fuelingConfiguration);
        FillingsEndPoint.broadcast(objectMapper.writeValueAsString(filling));
    }

}
