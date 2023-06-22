package com.devforce.liceman.solicitud.application;

import com.devforce.liceman.shared.application.MapperUtils;
import com.devforce.liceman.shared.application.UserUtils;
import com.devforce.liceman.shared.exceptions.SolicitudNotExistsException;
import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.domain.enums.Status;
import com.devforce.liceman.solicitud.domain.repository.SolicitudRepository;
import com.devforce.liceman.solicitud.infrastructure.dto.*;
import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.domain.enums.Area;
import com.devforce.liceman.usuario.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.devforce.liceman.solicitud.domain.enums.Status.*;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    public final SolicitudRepository solicitudRepository;
    public final MapperUtils mapperUtils;
    public final UserUtils userUtils;
    public static final Logger logger = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    @Override
    public Solicitud createSolicitud (SolicitudCreationRequestDTO solicitudCreationRequestDTO) {
        Solicitud newSolicitud = new Solicitud();
        newSolicitud.setArea(solicitudCreationRequestDTO.getArea());
        newSolicitud.setUserComment(solicitudCreationRequestDTO.getUserComment());
        newSolicitud.setUserId(userUtils.getLoggedUser());
        newSolicitud.setCreationDate(LocalDateTime.now());
        newSolicitud.setStatus(Status.PENDIENTE_MENTOR);
        return solicitudRepository.save(newSolicitud);
    }

    @Override
    public List<SolicitudDTO> getSolicitudes (){
        User user = userUtils.getLoggedUser();
        Role role = user.getRole();

        return getSolicitudesAccordingToRole(user, role);
    }

    private List<SolicitudDTO> getSolicitudesAccordingToRole (User user, Role role) {
        if (role == Role.USER) {
            // Obtener las solicitudes del usuario logueado
            return findAllSolicitudesByUser(user);
        } else if (role == Role.MENTOR) {
            // Obtener las solicitudes del área del usuario (mentor) logueado
            return findAllSolicitudesByArea(user.getArea());
        } else  {
            // Obtener todas las solicitudes
            return findAllSolicitudes();
        }
    }

    private List<SolicitudDTO> findAllSolicitudesByUser (User user) {
        return solicitudRepository.findAllByUserIdIs(user).stream()
                .map(mapperUtils::mapperToSolicitudUserResponseDTO)
                .collect(Collectors.toList());
    }

    private List<SolicitudDTO> findAllSolicitudesByArea (Area area) {
        return solicitudRepository.findAllByArea(area).stream()
                .map(mapperUtils::mapperToSolicitudUserResponseDTO)
                .collect(Collectors.toList());
    }


    private List<SolicitudDTO> findAllSolicitudes () {
        return solicitudRepository.findAll().stream()
                .map(mapperUtils::mapperToSolicitudUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SolicitudDTO> getSolicitudById (Long id) throws IllegalAccessException {
        User user = userUtils.getLoggedUser();
        Role role = user.getRole();
        Optional<Solicitud> solicitud = Optional.ofNullable(solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new));
        if (role == Role.USER && user.getId().equals(solicitud.get().getUserId().getId()) ||
                role == Role.MENTOR && solicitud.get().getArea()==user.getArea() ||
                role== Role.ADMIN) {
            return solicitud.map(mapperUtils::mapperToSolicitudUserResponseDTO);
        } else  {
            throw new IllegalAccessException();
        }
    }

    @Override
    public SolicitudDTO mentorUpdateSolicitud (Long id, UpdateMentorSolicitudDTO request) {
        try {
            Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new);
            if(!solicitud.getStatus().equals(PENDIENTE_MENTOR)){
                throw new IllegalStateException();
            }
            UpdateSolicitudFromMentorRequest(solicitud, request);
            return mapperUtils.mapperToSolicitudUserResponseDTO(solicitudRepository.save(solicitud));
        } catch (SolicitudNotExistsException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new SolicitudNotExistsException();
        } catch (IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        }
    }

    @Override
    public SolicitudDTO userUpdateSolicitud (Long id, UpdateUserSolicitudDTO request) {
        try {
            Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new);
            if(!solicitud.getStatus().equals(PENDIENTE_USER)){
                throw new IllegalStateException();
            }
            if(request.getStatus().equals(PENDIENTE_ADMIN)){
                solicitud.setStatus(request.getStatus());
            } else{
                solicitud.setStatus(RECHAZADA);
            }

            return mapperUtils.mapperToSolicitudUserResponseDTO(solicitudRepository.save(solicitud));
        } catch (SolicitudNotExistsException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new SolicitudNotExistsException();
        } catch (IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        }
    }

    @Override
    public SolicitudDTO adminUpdateSolicitud (Long id, UpdateAdminSolicitudDTO request) {
        try {
            Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new);
            if(!solicitud.getStatus().equals(PENDIENTE_ADMIN)){
                throw new IllegalStateException();
            }
            if(request.getStatus().equals(APROBADA)){
                solicitud.setStatus(request.getStatus());
            } else{
                solicitud.setStatus(RECHAZADA);
            }
            solicitud.setApprovedDate(LocalDateTime.now());
            solicitud.setAdminId(userUtils.getLoggedUser());
            solicitud.setEndDate(solicitud.getApprovedDate().plusDays(solicitud.getDays()));
            return mapperUtils.mapperToSolicitudUserResponseDTO(solicitudRepository.save(solicitud));
        } catch (SolicitudNotExistsException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new SolicitudNotExistsException();
        } catch (IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        }
    }

    private void UpdateSolicitudFromMentorRequest (Solicitud solicitud, UpdateMentorSolicitudDTO request) {
        solicitud.setMentorId(userUtils.getLoggedUser());
        solicitud.setMentorComment(request.getMentorComment());
        solicitud.setDays(request.getDays());
        solicitud.setLink(request.getLink());
        if(request.getStatus().equals(PENDIENTE_USER)){
            solicitud.setStatus(request.getStatus());
        } else{
            solicitud.setStatus(RECHAZADA);
        }
    }


}
