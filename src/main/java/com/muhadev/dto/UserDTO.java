package com.muhadev.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Email(message = "El formato de correo electrónico es inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Schema(example = "usuario@ejemplo.com", description = "Correo electrónico del usuario")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$", message = "La contraseña debe contener al menos una mayúscula, un número y un carácter especial.")
    @Schema(example = "P@ssw0rd123", description = "Contraseña del usuario")
    private String password;
}