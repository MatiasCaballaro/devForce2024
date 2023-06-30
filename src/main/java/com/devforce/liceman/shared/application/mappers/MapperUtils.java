package com.devforce.liceman.shared.application.mappers;

import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.infrastructure.dto.SolicitudDTO;
import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseWithoutSolicitudDTO;

public interface MapperUtils {

    UserResponseDTO MapperToUserDTO (User user);

    UserResponseWithoutSolicitudDTO MapperToUserWithoutSolicitudDTO (User user);

    SolicitudDTO mapperToSolicitudUserResponseDTO (Solicitud solicitud);

    
}
