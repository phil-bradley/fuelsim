/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.domain;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PBradley
 */
public enum PumpState {

    IDLE("IDL"),
    CONFIGERROR("CER"),
    REQUESTING("REQ"),
    RESERVED("RES"),
    RESERVEDREQUESTING("RRQ"),
    RELEASED("RLS"),
    AUTORELEASED("ARL"),
    FILLING("FLG"),
    BLOCKED("BLK"),
    STOPPED("STP"),
    OFFLINE("OXL"),
    MINORERROR("XER"),
    MAJORERROR("XER"),
    UNAVAILABLE("UNV"),
    COMPLETE("CMP");

    private final String code;

    public String code() {
        return code;
    }

    PumpState(String code) {
        this.code = code;
    }

    private static final Map<String, PumpState> codeToEnum = new HashMap<>();

    static {
        for (PumpState utype : values()) {
            codeToEnum.put(utype.code(), utype);
        }
    }

    public static PumpState fromCode(String code) {
        return codeToEnum.get(code);
    }
}
