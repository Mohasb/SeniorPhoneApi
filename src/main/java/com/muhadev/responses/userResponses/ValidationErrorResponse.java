package com.muhadev.responses.userResponses;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta con errores de validación")
public class ValidationErrorResponse {
    private boolean success;
    private int status;
    private String message;
    private Map<String, String> data; 
}
