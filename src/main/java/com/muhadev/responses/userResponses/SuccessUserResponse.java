package com.muhadev.responses.userResponses;

import com.muhadev.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta con datos de usuario")
public class SuccessUserResponse {
    private boolean success;
    private int status;
    private String message;
    private User data;
}
