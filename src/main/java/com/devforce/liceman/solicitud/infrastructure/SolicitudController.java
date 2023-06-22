package com.devforce.liceman.solicitud.infrastructure;

import com.devforce.liceman.shared.application.MapperUtils;
import com.devforce.liceman.shared.exceptions.SolicitudNotExistsException;
import com.devforce.liceman.shared.infrastructure.ResponseDTO;
import com.devforce.liceman.solicitud.application.SolicitudService;
import com.devforce.liceman.solicitud.infrastructure.dto.SolicitudCreationRequestDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateAdminSolicitudDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateMentorSolicitudDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateUserSolicitudDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body(
                new ResponseDTO(true, "Solicitudes Obtenidas", solicitudService.getSolicitudes()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('solicitud:read')")
    public ResponseEntity<ResponseDTO> getSolicitudByID (@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Solicitudes Obtenidas", solicitudService.getSolicitudById(id)));
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
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Solicitud Actualizada!", solicitudService.mentorUpdateSolicitud(id, updateMentorSolicitudDTO)));
        } catch (SolicitudNotExistsException e) {
            return ResponseEntity.ok().body(
                    new ResponseDTO(false, "Solicitud no existe!", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }

    }

    @Operation(
            description = "Enums de status que debería volver la operación del mentor : PENDIENTE_ADMIN || RECHAZADA"
    )
    @PutMapping("/{id}/user")
    @PreAuthorize("hasAuthority('solicitud:update')")
    public ResponseEntity<ResponseDTO> updateSolicitudbyUser (@PathVariable Long id, @RequestBody UpdateUserSolicitudDTO updateUserSolicitudDTO) {
        try {
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Solicitud Actualizada!", solicitudService.userUpdateSolicitud(id, updateUserSolicitudDTO)));
        } catch (SolicitudNotExistsException e) {
            return ResponseEntity.ok().body(
                    new ResponseDTO(false, "Solicitud no existe!", null));
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
            return ResponseEntity.ok().body(
                    new ResponseDTO(true, "Solicitud Actualizada!", solicitudService.adminUpdateSolicitud(id, updateAdminSolicitudDTO)));
        } catch (SolicitudNotExistsException e) {
            return ResponseEntity.ok().body(
                    new ResponseDTO(false, "Solicitud no existe!", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(false, e.getClass().getSimpleName(), null));
        }
    }


}



