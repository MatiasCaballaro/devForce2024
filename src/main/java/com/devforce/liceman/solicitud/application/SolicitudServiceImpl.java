package com.devforce.liceman.solicitud.application;

import com.devforce.liceman.shared.application.loggeduser.LoggedUser;
import com.devforce.liceman.shared.application.loggeduser.UserContext;
import com.devforce.liceman.shared.exceptions.SolicitudNotExistsException;
import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.domain.enums.Status;
import com.devforce.liceman.solicitud.domain.repository.SolicitudRepository;
import com.devforce.liceman.solicitud.infrastructure.dto.*;
import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.domain.enums.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.devforce.liceman.solicitud.domain.enums.Status.*;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    public final SolicitudRepository solicitudRepository;
    public static final Logger logger = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    @LoggedUser
    @Override
    public Solicitud createSolicitud (SolicitudCreationRequestDTO solicitudCreationRequestDTO) {
        Solicitud newSolicitud = new Solicitud();
        newSolicitud.setArea(solicitudCreationRequestDTO.getArea());
        newSolicitud.setUserComment(solicitudCreationRequestDTO.getUserComment());
        newSolicitud.setUserId(UserContext.getUser());
        newSolicitud.setCreationDate(LocalDateTime.now());
        newSolicitud.setStatus(Status.PENDIENTE_MENTOR);
        return solicitudRepository.save(newSolicitud);
    }

    @LoggedUser
    @Override
    public List<Solicitud> getSolicitudes () {
        return getSolicitudesAccordingToRole(UserContext.getUser());
    }

    private List<Solicitud> getSolicitudesAccordingToRole (User user) {

        if (user.getRole() == Role.USER) {
            return getAllSolicitudesByUser(user); // Get all solicitudes created by the logged user
        } else if (user.getRole() == Role.MENTOR) {
            return getAllSolicitudesByArea(user.getArea()); // Get all solicitudes from the same area of the logged Mentor's area
        } else {
            return getAllSolicitudes(); // Get all solicitudes (Admin)
        }
    }

    private List<Solicitud> getAllSolicitudesByUser (User user) {
        return solicitudRepository.findAllByUserIdIs(user);
    }

    private List<Solicitud> getAllSolicitudesByArea (Area area) {
        return solicitudRepository.findAllByArea(area);
    }

    private List<Solicitud> getAllSolicitudes () {
        return solicitudRepository.findAll();
    }

    @LoggedUser
    @Override
    public Solicitud getSolicitudById (Long id) throws IllegalAccessException {
        User user = UserContext.getUser();
        Role role = user.getRole();
        Solicitud solicitud = getSolicitudOrException(id);
        if (role == Role.USER && user.getId().equals(solicitud.getUserId().getId()) ||
                role == Role.MENTOR && solicitud.getArea() == user.getArea() ||
                role == Role.ADMIN) {
            return solicitud;
        } else {
            throw new IllegalAccessException();
        }
    }

    @LoggedUser
    @Override
    public Solicitud mentorUpdateSolicitud (Long id, UpdateMentorSolicitudDTO request) throws Exception {
        try {
            Solicitud solicitud = getSolicitudOrException(id);
            checkValidSolicitudStatus(solicitud.getStatus(), PENDIENTE_MENTOR);
            checkValidSolicitudMentorArea(solicitud.getArea());
            UpdateSolicitudFromMentorRequest(solicitud, request);
            return solicitudRepository.save(solicitud);
        } catch (SolicitudNotExistsException | IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        } catch (Exception e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new Exception(e);
        }
    }

    @LoggedUser
    @Override
    public Solicitud userUpdateSolicitud (Long id, UpdateUserSolicitudDTO request) throws Exception {
        try {
            Solicitud solicitud = getSolicitudOrException(id);
            checkValidSolicitudStatus(solicitud.getStatus(), PENDIENTE_USER);
            checkValidUser(solicitud.getUserId().getId());
            handledSolicitudStatusUpdate(solicitud, request.getStatus(), PENDIENTE_ADMIN);
            return solicitudRepository.save(solicitud);
        } catch (SolicitudNotExistsException | IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        } catch (Exception e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new Exception(e);
        }
    }

    @LoggedUser
    @Override
    public Solicitud adminUpdateSolicitud (Long id, UpdateAdminSolicitudDTO request) throws Exception {
        try {
            Solicitud solicitud = getSolicitudOrException(id);
            checkValidSolicitudStatus(solicitud.getStatus(), PENDIENTE_ADMIN);
            UpdateSolicitudFromAdminRequest(solicitud, request);
            return solicitudRepository.save(solicitud);
        } catch (SolicitudNotExistsException | IllegalStateException e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new IllegalStateException();
        } catch (Exception e) {
            logger.error(e.getMessage() + " - id=" + id);
            throw new Exception(e);
        }
    }


    private void UpdateSolicitudFromMentorRequest (Solicitud solicitud, UpdateMentorSolicitudDTO request) {
        solicitud.setMentorId(UserContext.getUser());
        solicitud.setMentorComment(request.getMentorComment());
        solicitud.setDays(request.getDays());
        solicitud.setLink(request.getLink());
        handledSolicitudStatusUpdate(solicitud, request.getStatus(), PENDIENTE_USER);
    }


    private void UpdateSolicitudFromAdminRequest (Solicitud solicitud, UpdateAdminSolicitudDTO request) {
        solicitud.setApprovedDate(LocalDateTime.now());
        solicitud.setAdminId(UserContext.getUser());
        solicitud.setEndDate(solicitud.getApprovedDate().plusDays(solicitud.getDays()));
        handledSolicitudStatusUpdate(solicitud, request.getStatus(), APROBADA);
    }

    private static void checkValidSolicitudStatus (Status solicitudStatus, Status expectedStatus) {
        if (!solicitudStatus.equals(expectedStatus)) {
            throw new IllegalStateException();
        }
    }

    private void checkValidSolicitudMentorArea (Area area) {
        if(!area.equals(UserContext.getUser().getArea())){
            throw new IllegalStateException();
        }
    }

    private void checkValidUser (Long userId) {
        if(!userId.equals(UserContext.getUser().getId())){
            throw new IllegalStateException();
        }
    }

    private Solicitud getSolicitudOrException (Long id) {
        return solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new);
    }

    private static void handledSolicitudStatusUpdate (Solicitud solicitud, Status requestStatus, Status expectedStatus) {
        if (requestStatus.equals(expectedStatus)) {
            solicitud.setStatus(requestStatus);
        } else {
            solicitud.setStatus(RECHAZADA);
        }
    }


}
