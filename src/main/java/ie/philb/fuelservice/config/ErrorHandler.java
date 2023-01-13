/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.config;

import ie.philb.fuelservice.domain.exception.FillingStateException;
import ie.philb.fuelservice.domain.exception.FuelConfigurationException;
import ie.philb.fuelservice.domain.exception.NoSuchItemException;
import ie.philb.fuelservice.domain.exception.PumpStateException;
import ie.philb.fuelservice.domain.exception.TankStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author PBradley
 */
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(PumpStateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public PumpStateException handlePumpStateException(PumpStateException ex) {
        return ex;
    }

    @ExceptionHandler(FillingStateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FillingStateException handleFillingStateException(FillingStateException ex) {
        return ex;
    }

    @ExceptionHandler(NoSuchItemException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NoSuchItemException handleNoSuchItemException(NoSuchItemException ex) {
        return ex;
    }

    @ExceptionHandler(TankStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public TankStateException handleTankStateException(TankStateException ex) {
        return ex;
    }

    @ExceptionHandler(FuelConfigurationException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public FuelConfigurationException handleFuelConfigException(FuelConfigurationException ex) {
        return ex;
    }
}
