package com.muhadev.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.muhadev.dto.UserDTO;
import com.muhadev.entity.User;
import com.muhadev.responses.SeniorApiResponse;
import com.muhadev.responses.userResponses.SuccessUserResponse;
import com.muhadev.responses.userResponses.ValidationErrorResponse;
import com.muhadev.services.UserService;
import com.muhadev.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Operation(summary = "Registrar un nuevo usuario", description = "Este endpoint permite registrar un usuario proporcionando su email y contraseña.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario registrado con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessUserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta, los datos no son válidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
			@ApiResponse(responseCode = "409", description = "El correo ya está registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeniorApiResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeniorApiResponse.class))) })
	@PostMapping("/register")
	public ResponseEntity<SeniorApiResponse<User>> register(@Valid @RequestBody UserDTO userDTO) {
		try {
			User newUser = userService.registerUser(userDTO);
			SeniorApiResponse<User> response = new SeniorApiResponse<>(true, HttpStatus.OK.value(),
					"Usuario registrado con éxito", newUser);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException ex) {
			SeniorApiResponse<User> response = new SeniorApiResponse<>(false, HttpStatus.CONFLICT.value(),
					ex.getMessage(), null);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	@Operation(summary = "Iniciar sesión", description = "Este endpoint permite iniciar sesión proporcionando email y contraseña.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeniorApiResponse.class))),
			@ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeniorApiResponse.class))) })
	public ResponseEntity<SeniorApiResponse<Map<String, String>>> login(@Valid @RequestBody UserDTO userDTO) {
		User user = userService.findByEmail(userDTO.getEmail());
		if (user == null || !passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					new SeniorApiResponse<>(false, HttpStatus.UNAUTHORIZED.value(), "Credenciales inválidas", null));
		}

		String token = jwtUtil.generateToken(user.getEmail());
		 Map<String, String> tokenData = new HashMap<>();
		   tokenData.put("token", token);
		SeniorApiResponse<Map<String, String>> response = new SeniorApiResponse<>(true, HttpStatus.OK.value(),
				"Inicio de sesión exitoso", tokenData);
		return ResponseEntity.ok(response);
	}

}
