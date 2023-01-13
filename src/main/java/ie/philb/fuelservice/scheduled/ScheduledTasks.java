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
import ie.philb.fuelservice.domain.Tank;
import ie.philb.fuelservice.domain.exception.FuelConfigurationException;
import ie.philb.fuelservice.domain.exception.NoSuchItemException;
import ie.philb.fuelservice.domain.exception.PumpStateException;
import ie.philb.fuelservice.util.RandomUtil;
import ie.philb.fuelservice.websocket.WsEndPoint;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author PBradley
 */
@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private FuelingConfiguration fuelingConfiguration;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FillingProgressManager fillingProgressManager;

    @Scheduled(fixedRate = 5 * 1000)
    public void broadcastStatus() {
        try {
            WsEndPoint.broadcast(objectMapper.writeValueAsString(fuelingConfiguration));
        } catch (JsonProcessingException ex) {

        }
    }

    @Scheduled(fixedRate = 5 * 1000)
    public void updateTankStatus() {

        for (Tank tank : fuelingConfiguration.getTanks()) {
            int tempChange = RandomUtil.randomBoolean() ? 1 : -1;
            int currentTemp = tank.getTemperatureCelcius();

            if (currentTemp == 18 && tempChange == -1) {
                tempChange = 0;
            }

            if (currentTemp == 28 && tempChange == 1) {
                tempChange = 0;
            }

            tank.setTemperatureCelcius(currentTemp + tempChange);
        }
    }

    @Scheduled(fixedRate = 1 * 1000)
    public void updatePumpStatus() throws NoSuchItemException {

        for (Pump pump : fuelingConfiguration.getPumps()) {

            List<Filling> pumpFillings = fuelingConfiguration.getPumpFillings(pump.getId());

            if (pump.getPumpState() == PumpState.REQUESTING) {
                if (RandomUtil.succeedWithProbability(0.1)) {
                    pump.setPumpState(PumpState.IDLE);
                    continue;
                }
            }

            if (pump.getPumpState() == PumpState.IDLE && pumpFillings.size() < 2) {

                if (RandomUtil.succeedWithProbability(0.1)) {
                    pump.setPumpState(PumpState.REQUESTING);
                    continue;
                }
            }

            if (pump.getPumpState() == PumpState.FILLING) {
                try {
                    Filling filling = fuelingConfiguration.getInProgressFilling(pump);

                    fillingProgressManager.updatePump(pump);
                } catch (PumpStateException | NoSuchItemException | FuelConfigurationException | JsonProcessingException ex) {
                    logger.error("Failed to udpate filling pump {}", pump, ex);
                }
                continue;
            }

            if (pump.getPumpState() == PumpState.STOPPED) {
                if (RandomUtil.succeedWithProbability(0.1)) {
                    pump.setPumpState(PumpState.IDLE);
                    continue;
                }
            }
        }
    }
}
