package com.devforce.liceman.solicitud.domain.repository;

import com.devforce.liceman.solicitud.domain.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

}
