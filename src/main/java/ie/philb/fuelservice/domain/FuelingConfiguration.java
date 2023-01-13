/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain;

import ie.philb.fuelservice.domain.exception.FillingStateException;
import ie.philb.fuelservice.domain.exception.FuelConfigurationException;
import ie.philb.fuelservice.domain.exception.NoSuchItemException;
import ie.philb.fuelservice.domain.exception.PumpStateException;
import ie.philb.fuelservice.util.RandomUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author PBradley
 */
@Component
@ConfigurationProperties
public class FuelingConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FuelingConfiguration.class);

    private final List<Tank> tanks = new ArrayList<>();
    private final List<Grade> grades = new ArrayList<>();
    private final List<Pump> pumps = new ArrayList<>();
    private final List<Filling> fillings = new ArrayList<>();

    public FuelingConfiguration() {
    }

    public Filling getFilling(int pumpId, FillingState fillingState) throws NoSuchItemException {

        for (Filling filling : fillings) {
            if (filling.getFillingStatus() == fillingState && filling.getPump().getId() == pumpId) {
                return filling;
            }
        }

        throw new NoSuchItemException("Cannot find filling on pump " + pumpId + " with state " + fillingState);
    }

    public void startPump(int pumpId) throws NoSuchItemException, FuelConfigurationException {
        Pump pump = getPumpById(pumpId);
        pump.setPumpState(PumpState.IDLE);
    }

    public void stopAllPumps() {

        for (Pump pump : getPumps()) {
            pump.setPumpState(PumpState.STOPPED);
        }
    }

    public void startAllPumps() {
        for (Pump pump : getPumps()) {
            pump.setPumpState(PumpState.IDLE);
        }
    }

    public void stopPump(int pumpId) throws NoSuchItemException, FuelConfigurationException, PumpStateException {
        Pump pump = getPumpById(pumpId);
        pump.setPumpState(PumpState.STOPPED);
    }

    public void claimFilling(int fillingId, int posId) throws NoSuchItemException, FillingStateException {

        Filling filling = getFillingById(fillingId);

        if (filling.getFillingStatus() != FillingState.PAYABLE) {
            throw new FillingStateException("Cannot claim filling " + filling);
        }

        filling.setFillingStatus(FillingState.CLAIMED);
        filling.setPosId(posId);
    }

    public void authoriseFilling(Filling filling) throws NoSuchItemException, FuelConfigurationException, PumpStateException {

        Pump pump = getPumpById(filling.getPump().getId());
        pump.setPumpState(PumpState.FILLING);
        fillings.add(filling);

        updateInProgressFilling(pump);
    }

    public Tank getTankById(int tankId) throws NoSuchItemException, FuelConfigurationException {

        if (tanks.isEmpty()) {
            throw new FuelConfigurationException("No tanks configured");
        }

        for (Tank tank : getTanks()) {

            if (tank.getId() == tankId) {
                return tank;
            }
        }

        throw new NoSuchItemException("Tank id " + tankId + " not found");

    }

    public Pump getPumpById(int pumpId) throws NoSuchItemException, FuelConfigurationException {

        if (pumps.isEmpty()) {
            throw new FuelConfigurationException("No pumps configured");
        }

        for (Pump pump : getPumps()) {

            if (pump.getId() == pumpId) {
                return pump;
            }
        }

        throw new NoSuchItemException("Pump id " + pumpId + " not found");
    }

    public Grade getGradeById(int gradeId) throws NoSuchItemException, FuelConfigurationException {

        if (grades.isEmpty()) {
            throw new FuelConfigurationException("No grades configured");
        }

        for (Grade grade : getGrades()) {

            if (grade.getId() == gradeId) {
                return grade;
            }
        }

        throw new NoSuchItemException("Grade id " + gradeId + " not found");
    }

    public Filling getFillingById(int fillingId) throws NoSuchItemException {

        for (Filling filling : fillings) {

            if (filling.getId() == fillingId) {
                return filling;
            }
        }

        throw new NoSuchItemException("Filling id " + fillingId + " not found");
    }

    private Filling getFillingByPump(int pumpId, FillingState fillingState) throws NoSuchItemException, FuelConfigurationException {

        if (pumps.isEmpty()) {
            throw new FuelConfigurationException("No pumps configured");
        }

        for (Filling filling : fillings) {

            if (filling.getPump().getId() == pumpId && filling.getFillingStatus() == fillingState) {
                return filling;
            }
        }

        throw new NoSuchItemException("Filling in pump " + pumpId + " with status " + fillingState + " not found");
    }

    public Filling getInProgressFilling(Pump pump) throws PumpStateException, NoSuchItemException, FuelConfigurationException {

        if (pump.getPumpState() != PumpState.FILLING) {
            throw new PumpStateException("Cannot complete filling on pump: " + pump);
        }

        Filling filling = getFillingByPump(pump.getId(), FillingState.INPROGRESS);
        return filling;
    }

    public void updateInProgressFilling(Pump pump) throws PumpStateException, NoSuchItemException, FuelConfigurationException {
        Filling filling = getInProgressFilling(pump);
        int newVolume = filling.getVolumeMilliLitres() + RandomUtil.randomInt(500, 5000);
        updateInProgressFilling(pump, newVolume);
    }
    
    public void updateInProgressFilling(Pump pump, int newVolume) throws PumpStateException, NoSuchItemException, FuelConfigurationException {
        Filling filling = getInProgressFilling(pump);
        filling.setVolumeMilliLitres(newVolume);

        int newTotalPrice = newVolume * filling.getTank().getGrade().getPriceTenthsPence();
        newTotalPrice = newTotalPrice / 10; // Adjust for 10s pence
        newTotalPrice = newTotalPrice / 1000; // Adjust for milli litres

        filling.setTotalPricePence((int) newTotalPrice);
    }

    public void clearFilling(Filling filling) {
        fillings.remove(filling);
    }

    public synchronized List<Tank> getTanks() {
        return Collections.unmodifiableList(new ArrayList<>(this.tanks));
    }

    public synchronized List<Grade> getGrades() {
        return Collections.unmodifiableList(new ArrayList<>(this.grades));
    }

    public synchronized List<Pump> getPumps() {
        return Collections.unmodifiableList(new ArrayList<>(this.pumps));
    }

    public List<Filling> getFillings() {
        return Collections.unmodifiableList(fillings);
    }
    
    public List<Filling> getPumpFillings(int pumpId) {
        List<Filling> pumpFillings = new ArrayList<>();
        
        for (Filling filling : fillings) {
            if (filling.getPump().getId() == pumpId) {
                pumpFillings.add(filling);
            }
        }
        
        return pumpFillings;
    }

    public synchronized void update(List<Pump> pumps, List<Tank> tanks, List<Grade> grades) {

        for (Pump pump : pumps) {
            if (!hasPump(pump.getId())) {
                this.pumps.add(pump);
            }
        }

        this.tanks.clear();
        this.tanks.addAll(tanks);

        this.grades.clear();
        this.grades.addAll(grades);
    }
    
    private synchronized boolean hasPump(int pumpId) {
        for (Pump pump : pumps) {
            if (pump.getId() == pumpId) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "FuelingConfiguration{" + "tanks=" + tanks + ", grades=" + grades + ", pumps=" + pumps + ", fillings=" + fillings + '}';
    }

}
