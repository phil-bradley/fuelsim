/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author PBradley
 */
@Converter(autoApply = true)
public class FillingStateConverter implements AttributeConverter<FillingState, String> {

    @Override
    public String convertToDatabaseColumn(FillingState state) {
        return state.code();
    }

    @Override
    public FillingState convertToEntityAttribute(String code) {
        return FillingState.fromCode(code);
    }

}
