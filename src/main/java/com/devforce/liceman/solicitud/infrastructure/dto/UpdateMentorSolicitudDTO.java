package com.devforce.liceman.solicitud.infrastructure.dto;

import com.devforce.liceman.solicitud.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMentorSolicitudDTO {

    private Integer days;
    private String link;
    private String mentorComment;
    private Status status;
}
