package com.devforce.liceman.shared.application.mappers;

import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.infrastructure.dto.SolicitudDTO;
import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseWithoutSolicitudDTO;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperUtilsImpl implements MapperUtils{

    @Override
    public UserResponseDTO MapperToUserDTO (User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstname(user.getFirstname());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setArea(user.getArea());
        userResponseDTO.setHasTeams(user.getHasTeams());
        userResponseDTO.setSolicitudes(user.getSolicitudes().stream().map(this::mapperToSolicitudUserResponseDTO).collect(Collectors.toList()));
        return userResponseDTO;
    }

    @Override
    public UserResponseWithoutSolicitudDTO MapperToUserWithoutSolicitudDTO (User user) {
        UserResponseWithoutSolicitudDTO userDTO = new UserResponseWithoutSolicitudDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setHasTeams(user.getHasTeams());
        return userDTO;
    }
    @Override
    public SolicitudDTO mapperToSolicitudUserResponseDTO (Solicitud solicitud) {
        SolicitudDTO solicitudDTO = SolicitudDTO.builder()
                .id(solicitud.getId())
                .area(solicitud.getArea())
                .creationDate(solicitud.getCreationDate())
                .status(solicitud.getStatus())
                .userComment(solicitud.getUserComment())
                .mentorComment(solicitud.getMentorComment())
                .days(solicitud.getDays())
                .link(solicitud.getLink())
                .approvedDate(solicitud.getApprovedDate())
                .endDate(solicitud.getEndDate())
                .userId(this.MapperToUserWithoutSolicitudDTO(solicitud.getUserId()))
                .build();
        if(solicitud.getMentorId()!=null){
            solicitudDTO.setMentorId(this.MapperToUserWithoutSolicitudDTO(solicitud.getMentorId()));
        }
        if(solicitud.getAdminId()!=null){
            solicitudDTO.setAdminId(this.MapperToUserWithoutSolicitudDTO(solicitud.getAdminId()));
        }
        return solicitudDTO;
    }
}
