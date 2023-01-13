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
public enum FillingState {

    PAYABLE("PBL"),
    CLAIMED("CLM"),
    PAID("PAD"),
    INPROGRESS("PRG"),
    CLAIMEDOTHER("CMO"),
    VALIDATIONERROR("ERR");

    private final String code;

    public String code() {
        return code;
    }

    FillingState(String code) {
        this.code = code;
    }

    private static final Map<String, FillingState> codeToEnum = new HashMap<>();

    static {
        for (FillingState utype : values()) {
            codeToEnum.put(utype.code(), utype);
        }
    }

    public static FillingState fromCode(String code) {
        return codeToEnum.get(code);
    }
}
