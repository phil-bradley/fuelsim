/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author PBradley
 */
@Entity
public class Pump implements Serializable {

    @Id
    private int id;

    private PumpState pumpState = PumpState.IDLE;

    public void setPumpState(PumpState state) {
        this.pumpState = state;
    }

    public PumpState getPumpState() {
        return pumpState;
    }

    public int getId() {
        return id;
    }
}
