package com.devforce.liceman.usuario.infrastructure.dto;

import com.devforce.liceman.solicitud.infrastructure.dto.SolicitudDTO;
import com.devforce.liceman.usuario.domain.enums.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Boolean hasTeams;
    private Area area;
    private List<SolicitudDTO> solicitudes;

}
