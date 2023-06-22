package com.devforce.liceman.solicitud.domain.repository;

import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.domain.enums.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findAllByUserIdIs(User user);
    List<Solicitud> findAllByArea (Area area);

}
