package com.devforce.liceman.solicitud.application;

import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.infrastructure.dto.SolicitudCreationRequestDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateAdminSolicitudDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateMentorSolicitudDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateUserSolicitudDTO;

import java.util.List;

public interface SolicitudService {

    Solicitud createSolicitud (SolicitudCreationRequestDTO solicitudCreationRequestDTO);

    List<Solicitud> getSolicitudes ();

    Solicitud getSolicitudById (Long id) throws IllegalAccessException;

    Solicitud mentorUpdateSolicitud (Long id, UpdateMentorSolicitudDTO updateMentorSolicitudDTO) throws Exception;

    Solicitud userUpdateSolicitud (Long id, UpdateUserSolicitudDTO updateUserSolicitudDTO) throws Exception;

    Solicitud adminUpdateSolicitud (Long id, UpdateAdminSolicitudDTO updateAdminSolicitudDTO) throws Exception;
}
