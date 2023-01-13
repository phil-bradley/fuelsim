/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain.exception;

/**
 *
 * @author PBradley
 */
public class NoSuchItemException extends Exception {
    
    public NoSuchItemException(String msg) {
        super(msg);
    }
    
    public NoSuchItemException(String msg, Throwable t) {
        super(msg, t);
    }
}
