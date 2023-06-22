package com.devforce.liceman.solicitud.infrastructure.dto;

import com.devforce.liceman.usuario.domain.enums.Area;
import com.devforce.liceman.solicitud.domain.enums.Status;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseWithoutSolicitudDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDTO {

    private Long id;

    @Enumerated(EnumType.STRING)
    private Area area;

    private LocalDateTime creationDate;

    private LocalDateTime approvedDate;

    private LocalDateTime endDate;

    private Integer days;

    private Status status;


    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "mentor_comment")
    private String mentorComment;

    private String link;

    private UserResponseWithoutSolicitudDTO userId;

    private UserResponseWithoutSolicitudDTO mentorId;

    private UserResponseWithoutSolicitudDTO adminId;

}
