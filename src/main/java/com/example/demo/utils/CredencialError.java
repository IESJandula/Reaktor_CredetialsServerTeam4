package com.example.demo.utils;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

@Data

@NoArgsConstructor
public class CredencialError extends Exception {
    private String code;

    public CredencialError(String message) {
        super(message);
    }


    public CredencialError(String code,String message ) {
        super(message);
        this.code = code;
    }
    public CredencialError(String code ,String message, Throwable e) {
        super(message,e);
        this.code = code;
    }
    /**
     * Devuelve un mapa con los detalles de la excepción:
     * código, mensaje y stacktrace de la causa si existe.
     */
    public Object getBodyExceptionMessage()
    {
        Map<String, Object> mapBodyException = new HashMap<>();
        mapBodyException.put("codigo", this.code);
        mapBodyException.put("message", this.getMessage());

        if (this.getCause() != null)
        {
            String stackTrace = ExceptionUtils.getStackTrace(this.getCause());
            mapBodyException.put("excepcion", stackTrace);
        }

        return mapBodyException;
    }

    public String getCode() {
        return code;
    }
}
