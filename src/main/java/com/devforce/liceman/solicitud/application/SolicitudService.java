package com.devforce.liceman.solicitud.application;

import com.devforce.liceman.solicitud.domain.Solicitud;

import java.util.List;

public interface SolicitudService {

    List<Solicitud> findAllSolicitudes();

}
