package com.devforce.liceman.solicitud.infrastructure;

import com.devforce.liceman.shared.application.mappers.MapperUtils;
import com.devforce.liceman.shared.exceptions.SolicitudNotExistsException;
import com.devforce.liceman.shared.infrastructure.ResponseDTO;
import com.devforce.liceman.solicitud.application.SolicitudService;
import com.devforce.liceman.solicitud.infrastructure.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/solicitudes")
@Tag(name = "Solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final MapperUtils mapperUtils;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('solicitud:create')")
    public ResponseEntity<ResponseDTO> createSolicitud (@RequestBody SolicitudCreationRequestDTO request) {
        return ResponseEntity.ok().body(
                new ResponseDTO(true,
                        "Solicitud creada!",
                        mapperUtils.mapperToSolicitudUserResponseDTO(solicitudService.createSolicitud(request))));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('solicitud:read')")
    public ResponseEntity<ResponseDTO> getSolicitudes () {
        return ResponseEntity.ok().body(new ResponseDTO(true, "Solicitudes Obtenidas", solicitudService.getSolicitudes()
                .stream()
                .map(mapperUtils::mapperToSolicitudUserResponseDTO)
                .collect(Collectors.toList())));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('solicitud:read')")
    public ResponseEntity<ResponseDTO> getSolicitudByID (@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Solicitudes Obtenidas", mapperUtils.mapperToSolicitudUserResponseDTO(solicitudService.getSolicitudById(id))));
        } catch (SolicitudNotExistsException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseDTO(false, null, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }

    }

    @Operation(
            description = "Enums de status que debería volver la operación del mentor : PENDIENTE_USER || RECHAZADA"
    )
    @PutMapping("/{id}/mentor")
    @PreAuthorize("hasAuthority('solicitud:update')")
    public ResponseEntity<ResponseDTO> updateSolicitudbyMentor (@PathVariable Long id, @RequestBody UpdateMentorSolicitudDTO updateMentorSolicitudDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Solicitud Actualizada!",
                            mapperUtils.mapperToSolicitudUserResponseDTO(solicitudService.mentorUpdateSolicitud(id, updateMentorSolicitudDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getMessage(), null));
        }

    }

    @Operation(
            description = "Enums de status que debería volver la operación del mentor : PENDIENTE_ADMIN || RECHAZADA"
    )
    @PutMapping("/{id}/user")
    @PreAuthorize("hasAuthority('solicitud:update')")
    public ResponseEntity<ResponseDTO> updateSolicitudbyUser (@PathVariable Long id, @RequestBody UpdateUserSolicitudDTO updateUserSolicitudDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Solicitud Actualizada!",
                            mapperUtils.mapperToSolicitudUserResponseDTO(solicitudService.userUpdateSolicitud(id, updateUserSolicitudDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


    @Operation(
            description = "Enums de status que debería volver la operación del mentor : APROBADA || RECHAZADA"
    )
    @PutMapping("/{id}/admin")
    @PreAuthorize("hasAuthority('solicitud:update')")
    public ResponseEntity<ResponseDTO> updateSolicitudbyAdmin (@PathVariable Long id, UpdateAdminSolicitudDTO updateAdminSolicitudDTO) {
        try {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(true,
                            "Solicitud Actualizada!",
                            mapperUtils.mapperToSolicitudUserResponseDTO(solicitudService.adminUpdateSolicitud(id, updateAdminSolicitudDTO))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


}



