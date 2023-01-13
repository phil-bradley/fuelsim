/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain.exception;

/**
 *
 * @author PBradley
 */
public class PumpStateException extends Exception {

    public PumpStateException(String msg) {
        super(msg);
    }

    public PumpStateException(String msg, Throwable t) {
        super(msg, t);
    }

}
