package com.devforce.liceman.solicitud.application;

import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.infrastructure.dto.*;

import java.util.List;
import java.util.Optional;

public interface SolicitudService {

    Solicitud createSolicitud (SolicitudCreationRequestDTO solicitudCreationRequestDTO);

    List<SolicitudDTO> getSolicitudes ();

    Optional<SolicitudDTO> getSolicitudById (Long id) throws IllegalAccessException;

    SolicitudDTO mentorUpdateSolicitud (Long id, UpdateMentorSolicitudDTO updateMentorSolicitudDTO);

    SolicitudDTO userUpdateSolicitud (Long id, UpdateUserSolicitudDTO updateUserSolicitudDTO);

    SolicitudDTO adminUpdateSolicitud (Long id, UpdateAdminSolicitudDTO updateAdminSolicitudDTO);
}
