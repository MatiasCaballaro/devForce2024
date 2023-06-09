package com.devforce.liceman.solicitud.application;

import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.domain.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService{

    SolicitudRepository solicitudRepository;

    @Override
    public List<Solicitud> findAllSolicitudes() {
        return solicitudRepository.findAll();
    }
}
