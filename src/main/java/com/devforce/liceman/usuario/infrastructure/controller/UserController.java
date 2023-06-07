package com.devforce.liceman.usuario.infrastructure.controller;

import com.devforce.liceman.security.application.AuthenticationService;
import com.devforce.liceman.usuario.application.UserService;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
//@PreAuthorize("hasRole('USER')")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Operation(
            description = "Devuelve la lista total de usuarios como UserResponseDTO"//,
            //summary = ""
    )
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(description = "Devuelve un usuario como UserResponseDTO")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public Optional<UserResponseDTO> getUserByID(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(description = "Crea un usuario a partir de un UserRequestDTO")

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(description = "Actualiza un usuario a partir de un UserRequestDTO")
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public UserResponseDTO updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateOwnUser(userRequestDTO);
    }

    @Operation(description = "Elimina un usuario a partir de un Long id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public void delete(@PathVariable Long id) {
        userService.deleteUserbyId(id);
    }
}
