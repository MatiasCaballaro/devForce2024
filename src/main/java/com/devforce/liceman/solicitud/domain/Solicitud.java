package com.devforce.liceman.solicitud.domain;

import com.devforce.liceman.solicitud.domain.enums.Area;
import com.devforce.liceman.solicitud.domain.enums.Status;
import com.devforce.liceman.usuario.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitudes")
public class Solicitud {
    //TODO: CHEQUEAR NOMBRES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Area area;

    //TODO: Hacer la relación ManyToOne
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "mentor_id")
    private Long mentorId;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "approved_date")
    private LocalDate approvedDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private Integer days;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "mentor_comment")
    private String mentorComment;
    //TODO: Ver si hacemos List<String> del comentario, quiza hacer una clase Comentario

    private String link;

}
