/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.service;

import ie.philb.fuelservice.domain.Grade;
import java.util.List;

/**
 *
 * @author PBradley
 */
public interface GradeService {

    List<Grade> getGrades();
    
    void save(Grade grade);
}
