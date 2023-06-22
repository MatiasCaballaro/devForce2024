package com.devforce.liceman.solicitud.infrastructure.dto;

import com.devforce.liceman.usuario.domain.enums.Area;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCreationRequestDTO {

    @Enumerated(EnumType.STRING)
    private Area area;

    //TODO: Ver si hacemos List<String> del comentario, quiza hacer una clase Comentario
    @Column(name = "user_comment")
    private String userComment;

    private String link;

}
