/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.philb.fuelservice.domain.Delivery;
import ie.philb.fuelservice.domain.Filling;
import ie.philb.fuelservice.domain.FillingState;
import ie.philb.fuelservice.domain.FuelingConfiguration;
import ie.philb.fuelservice.domain.Grade;
import ie.philb.fuelservice.domain.Pump;
import ie.philb.fuelservice.domain.PumpState;
import ie.philb.fuelservice.domain.Tank;
import ie.philb.fuelservice.domain.exception.FuelConfigurationException;
import ie.philb.fuelservice.domain.exception.NoSuchItemException;
import ie.philb.fuelservice.domain.exception.PumpStateException;
import ie.philb.fuelservice.domain.exception.TankStateException;
import ie.philb.fuelservice.service.DeliveryService;
import ie.philb.fuelservice.service.FillingService;
import ie.philb.fuelservice.service.GradeService;
import ie.philb.fuelservice.service.TankService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ie.philb.fuelservice.service.FuelingConfigurationService;
import ie.philb.fuelservice.util.RandomUtil;
import ie.philb.fuelservice.websocket.FillingsEndPoint;
import java.util.List;

/**
 *
 * @author PBradley
 */
@RestController()
public class ForeCourtActionsController {

    private static final Logger logger = LoggerFactory.getLogger(ForeCourtActionsController.class);

    @Autowired
    private FuelingConfiguration fuelingConfiguration;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private TankService tankService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private FillingService fillingService;

    @Autowired
    private FuelingConfigurationService fuelingConfigurationService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(path = "authorise", method = RequestMethod.POST)
    public void authorisePump(@RequestParam(required = true) int pumpId) throws NoSuchItemException, FuelConfigurationException, PumpStateException {

        Pump pump = fuelingConfiguration.getPumpById(pumpId);
        Tank tank = getRandomTank();

        if (pump.getPumpState() != PumpState.REQUESTING) {
            throw new PumpStateException("Cannot authorise pump " + pumpId + " with state " + pump.getPumpState());
        }

        if (tank.getVolumeLitres() < 1000) {
            throw new PumpStateException("Cannot authorise pump " + pumpId + " with volume " + tank.getVolumeLitres());
        }

        Filling filling = createFilling(pump, tank);
        filling = fillingService.save(filling);
        fuelingConfiguration.authoriseFilling(filling);
    }

    @RequestMapping(path = "stop", method = RequestMethod.POST)
    public void stopPump(@RequestParam(required = true) int pumpId) throws NoSuchItemException, FuelConfigurationException, PumpStateException {
        fuelingConfiguration.stopPump(pumpId);
    }

    @RequestMapping(path = "start", method = RequestMethod.POST)
    public void startPump(@RequestParam(required = true) int pumpId) throws NoSuchItemException, FuelConfigurationException, PumpStateException {
        fuelingConfiguration.startPump(pumpId);
    }

    @RequestMapping(path = "stopAll", method = RequestMethod.POST)
    public void stopAllPumps() {
        fuelingConfiguration.stopAllPumps();
    }

    @RequestMapping(path = "startAll", method = RequestMethod.POST)
    public void startAllPumps() {
        fuelingConfiguration.startAllPumps();
    }

    @RequestMapping(path = "claim", method = RequestMethod.POST)
    public void claimFilling(@RequestParam(required = true) int fillingId, @RequestParam(required = true) int posId) throws NoSuchItemException, FuelConfigurationException, PumpStateException {

        Filling filling = fuelingConfiguration.getFillingById(fillingId);

        if (filling.getFillingStatus() == FillingState.PAYABLE) {
            filling.setPosId(posId);
            filling.setFillingStatus(FillingState.CLAIMED);
        }

    }

    @RequestMapping(path = "unclaim", method = RequestMethod.POST)
    public void unclaimFilling(@RequestParam(required = true) int fillingId, @RequestParam(required = true) int posId) throws NoSuchItemException, FuelConfigurationException, PumpStateException {

        Filling filling = fuelingConfiguration.getFillingById(fillingId);

        if (filling.getFillingStatus() == FillingState.CLAIMED && filling.getPosId() == posId) {
            filling.setPosId(0);
            filling.setFillingStatus(FillingState.PAYABLE);
        }

    }

    @RequestMapping(path = "clear", method = RequestMethod.POST)
    public void clearFilling(@RequestParam(required = true) int fillingId) throws NoSuchItemException {

        Filling filling = fuelingConfiguration.getFillingById(fillingId);

        if (filling.getFillingStatus() != FillingState.CLAIMED) {
            throw new NoSuchItemException("Filling " + fillingId + " cannot be cleared with state " + filling.getFillingStatus());
        }

        fillingService.clearFilling(filling);
        fuelingConfiguration.clearFilling(filling);

    }

    @RequestMapping(path = "processFilling", method = RequestMethod.POST)
    public Filling processFilling(
            @RequestParam(required = true) int pumpId,
            @RequestParam(required = true) int tankId,
            @RequestParam(required = true) int volumeMillis,
            @RequestParam(required = true) int posId,
            @RequestParam(required = true) int totalPricePence) throws NoSuchItemException, FuelConfigurationException, TankStateException, Exception {

        Pump pump = fuelingConfiguration.getPumpById(pumpId);
        Tank tank = fuelingConfiguration.getTankById(tankId);

        Filling filling = new Filling();
        filling.setFillingStatus(FillingState.PAID);
        filling.setStarted(new Date());
        filling.setCompleted(new Date());
        filling.setPaid(new Date());
        filling.setVolumeMilliLitres(volumeMillis);
        filling.setPosId(posId);
        filling.setTank(tank);
        filling.setPump(pump);
        filling.setTotalPricePence(totalPricePence);

        int currentVolume = tank.getVolumeLitres();
        int newVolume = currentVolume - (filling.getVolumeMilliLitres() / 1000);

        if (newVolume < 1000) {
            String msg = "Cannot create filling of volume " + volumeMillis + "mL from tank " + tankId + " with volume " + currentVolume + "L";
            throw new TankStateException(msg);
        }

        Filling saved = fillingService.save(filling);
        fillingService.completeFilling(filling);
        fillingService.clearFilling(saved);

        fuelingConfigurationService.updateConfiguration(fuelingConfiguration);
        FillingsEndPoint.broadcast(objectMapper.writeValueAsString(saved));
        return fillingService.getById(filling.getId());
    }

    @RequestMapping(path = "setPrice", method = RequestMethod.POST)
    public void setPrice(@RequestParam(required = true) int gradeId, @RequestParam(required = true) int priceTenthsPence) throws NoSuchItemException, FuelConfigurationException {

        Grade grade = fuelingConfiguration.getGradeById(gradeId);
        grade.setPriceTenthsPence(priceTenthsPence);
        logger.info("Setting price to {} on grade {}.", priceTenthsPence, grade);

        gradeService.save(grade);
        fuelingConfigurationService.updateConfiguration(fuelingConfiguration);
    }

    @RequestMapping(path = "deliver", method = RequestMethod.POST)
    public Delivery deliver(@RequestParam(required = true) int tankId, @RequestParam(required = true) int gradeId, @RequestParam(required = true) int volumeLitres) throws NoSuchItemException, FuelConfigurationException, PumpStateException {

        Tank tank = tankService.getById(tankId);

        if (gradeId != 0 && gradeId != tank.getGrade().getId()) {
            throw new FuelConfigurationException("Cannot apply delivery of grade " + gradeId + " to tank " + tankId + " with grade " + tank.getGrade().getId());
        }

        if (volumeLitres <= 0) {
            throw new FuelConfigurationException("Cannot apply delivery of volume " + volumeLitres + " to tank " + tank);
        }

        int newVolume = tank.getVolumeLitres() + volumeLitres;

        if (newVolume > tank.getCapacityLitres()) {
            throw new FuelConfigurationException("Cannot apply delivery of volume " + volumeLitres + " to tank " + tank);
        }

        Delivery delivery = new Delivery();
        delivery.setAmountLitres(volumeLitres);
        delivery.setTank(tank);
        delivery.setCreated(new Date());
        delivery.setVolumePreDelivery(tank.getVolumeLitres());
        delivery.setVolumePostDelivery(newVolume);

        tank.setVolumeLitres(tank.getVolumeLitres() + delivery.getAmountLitres());
        tankService.save(tank);

        fuelingConfigurationService.updateConfiguration(fuelingConfiguration);

        return deliveryService.save(delivery);
    }

    private Tank getRandomTank() throws FuelConfigurationException {

        List<Tank> tanks = fuelingConfiguration.getTanks();

        if (tanks.isEmpty()) {
            throw new FuelConfigurationException("No tanks configured");
        }

        int idx = RandomUtil.randomInt(0, tanks.size() - 1);
        return tanks.get(idx);
    }

    private Filling createFilling(Pump pump, Tank tank) {
        Filling filling = new Filling();
        filling.setFillingStatus(FillingState.INPROGRESS);
        filling.setPump(pump);
        filling.setStarted(new Date());
        filling.setVolumeMilliLitres(0);
        filling.setTank(tank);
        return filling;
    }
}
