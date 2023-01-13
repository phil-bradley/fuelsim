/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author PBradley
 */
@Data
@Entity
public class Tank implements Serializable {

    @Id
    private int id;

    private int capacityLitres;
    private int volumeLitres;
    private int temperatureCelcius;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastUpdated = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    private Grade grade;

}
