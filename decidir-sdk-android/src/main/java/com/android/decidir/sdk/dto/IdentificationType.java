package com.android.decidir.sdk.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by biandra on 03/08/17.
 */

public enum IdentificationType {

    DNI,
    CI,
    LE,
    LC;

    private static Map<String, IdentificationType> IdTypeMap = new HashMap<>(4);
    static {
        IdTypeMap.put("dni", DNI);
        IdTypeMap.put("ci", CI);
        IdTypeMap.put("le", LE);
        IdTypeMap.put("lc", LC);
    }

    @JsonCreator
    public static IdentificationType forValue(String value) {
        return IdTypeMap.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, IdentificationType> entry : IdTypeMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }
        return null;
    }
}
