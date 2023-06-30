package com.devforce.liceman;

import com.devforce.liceman.shared.exceptions.SolicitudNotExistsException;
import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.solicitud.domain.enums.Status;
import com.devforce.liceman.solicitud.domain.repository.SolicitudRepository;
import com.devforce.liceman.solicitud.infrastructure.dto.SolicitudCreationRequestDTO;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateUserSolicitudDTO;
import com.devforce.liceman.usuario.application.UserService;
import com.devforce.liceman.usuario.domain.enums.Area;
import com.devforce.liceman.usuario.domain.repository.UserRepository;
import com.devforce.liceman.solicitud.infrastructure.dto.UpdateMentorSolicitudDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.devforce.liceman.solicitud.domain.enums.Status.PENDIENTE_USER;
import static com.devforce.liceman.solicitud.domain.enums.Status.RECHAZADA;
import static com.devforce.liceman.usuario.domain.enums.Role.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Value("${sample.data}")
    private Boolean sampleData;

    private final UserService userService;

    private final SolicitudRepository solicitudRepository;

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {


        if (sampleData) {

            System.out.println("Test Data Initializer begins!  ");

            System.out.println("================================================================================");
            System.out.println("USER CREATION");
            System.out.println("================================================================================");

            UserRequestDTO user1 = UserRequestDTO.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user@mail.com")
                    .password("password")
                    .role(USER)
                    .hasTeams(true)
                    .build();
            System.out.println("User1 token: " + userService.createUser(user1).getAccessToken());

            UserRequestDTO user2 = UserRequestDTO.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user2@mail.com")
                    .password("password")
                    .role(USER)
                    .hasTeams(true)
                    .build();
            System.out.println("User2 token: " + userService.createUser(user2).getAccessToken());


            UserRequestDTO mentor1 = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("mentor@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .area(Area.BACKEND)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor1 (BACKEND) token: " + userService.createUser(mentor1).getAccessToken());

            UserRequestDTO mentor2 = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("mentor2@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .area(Area.BACKEND)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor2 (BACKEND) token: " + userService.createUser(mentor2).getAccessToken());

            UserRequestDTO mentor3 = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("mentor3@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .area(Area.FRONTEND)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor3 (FRONTEND) token: " + userService.createUser(mentor3).getAccessToken());

            UserRequestDTO admin = UserRequestDTO.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .hasTeams(true)
                    .build();

            System.out.println("Admin token: " + userService.createUser(admin).getAccessToken());


            System.out.println("================================================================================");
            System.out.println("SOLICITUD CREATION");
            System.out.println("================================================================================");

            SolicitudCreationRequestDTO solicitud1 = SolicitudCreationRequestDTO.builder()
                    .userComment("Necesito estudiar JAVA")
                    .area(Area.BACKEND)
                    .build();
            System.out.println("Solicitud1 (BACKEND)" + createSolicitud(solicitud1,user1));

            SolicitudCreationRequestDTO solicitud2 = SolicitudCreationRequestDTO.builder()
                    .userComment("Necesito estudiar SPRING BOOT")
                    .area(Area.BACKEND)
                    .build();
            System.out.println("Solicitud1 (BACKEND)" + createSolicitud(solicitud2,user1));

            SolicitudCreationRequestDTO solicitud3 = SolicitudCreationRequestDTO.builder()
                    .userComment("Necesito estudiar REACT")
                    .area(Area.FRONTEND)
                    .build();
            System.out.println("Solicitud1 (FRONTEND)" + createSolicitud(solicitud3,user1));

            SolicitudCreationRequestDTO solicitud4 = SolicitudCreationRequestDTO.builder()
                    .userComment("Necesito estudiar SQL")
                    .area(Area.DATA)
                    .build();
            System.out.println("Solicitud1 (BACKEND)" + createSolicitud(solicitud4,user2));


            System.out.println("================================================================================");
            System.out.println("SOLICITUD UPDATED BY MENTOR CREATION");
            System.out.println("================================================================================");

            UpdateMentorSolicitudDTO solicitudMentor1= UpdateMentorSolicitudDTO.builder()
                    .mentorComment("Te sugiero que veas el curso de udemy que adjunto en el link. Si estás de acuerdo" +
                            "acepta la solicitud, o puedes contactarme por Teams. Éxitos!")
                    .link("#")
                    .days(30)
                    .status(Status.PENDIENTE_USER)
                    .build();
            System.out.println("Solicitud1 Actualizada por Mentor1" + updateSolicitudMentor(1L,solicitudMentor1,mentor1));


            UpdateMentorSolicitudDTO solicitudMentor2= UpdateMentorSolicitudDTO.builder()
                    .mentorComment("Te sugiero que veas el curso de udemy que adjunto en el link. Si estás de acuerdo" +
                            "acepta la solicitud, o puedes contactarme por Teams. Éxitos!")
                    .link("#")
                    .days(45)
                    .status(Status.PENDIENTE_USER)
                    .build();
            System.out.println("Solicitud1 Actualizada por Mentor1" + updateSolicitudMentor(2L,solicitudMentor2,mentor1));


            System.out.println("================================================================================");
            System.out.println("SOLICITUD UPDATED BY USER CREATION");
            System.out.println("================================================================================");

            //TODO crear
            UpdateUserSolicitudDTO solicitudUser1= UpdateUserSolicitudDTO.builder()
                    .status(Status.PENDIENTE_ADMIN)
                    .build();
            System.out.println("Solicitud1 Actualizada por User1" + UpdateUserSolicitudDTO(1L,solicitudUser1));


            System.out.println("================================================================================");
            System.out.println("SOLICITUD UPDATED BY ADMIN CREATION");
            System.out.println("================================================================================");

            //TODO crear


            System.out.println("Test Data Initializer finished! ");
        }
    }

    private Solicitud createSolicitud (SolicitudCreationRequestDTO solicitudCreationRequestDTO, UserRequestDTO userDTO) {
        Solicitud newSolicitud = new Solicitud();
        newSolicitud.setArea(solicitudCreationRequestDTO.getArea());
        newSolicitud.setUserComment(solicitudCreationRequestDTO.getUserComment());
        newSolicitud.setCreationDate(LocalDateTime.now());
        newSolicitud.setStatus(Status.PENDIENTE_MENTOR);
        newSolicitud.setUserId(userRepository.findByEmail(userDTO.getEmail()).orElseThrow(NoSuchElementException::new));
        return solicitudRepository.save(newSolicitud);
    }

    private Solicitud updateSolicitudMentor (Long id, UpdateMentorSolicitudDTO request, UserRequestDTO userDTO) {
        try {
            Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new);
            solicitud.setMentorId(userRepository.findByEmail(userDTO.getEmail()).orElseThrow(NoSuchElementException::new));
            solicitud.setMentorComment(request.getMentorComment());
            solicitud.setDays(request.getDays());
            solicitud.setLink(request.getLink());
            if(request.getStatus().equals(PENDIENTE_USER)){
                solicitud.setStatus(request.getStatus());
            } else{
                solicitud.setStatus(RECHAZADA);
            }
            return solicitudRepository.save(solicitud);
        } catch (Exception e) {
            throw new SolicitudNotExistsException();
        }
    }

    private Solicitud UpdateUserSolicitudDTO (Long id,  UpdateUserSolicitudDTO request) {
        try {
            Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(SolicitudNotExistsException::new);
            solicitud.setStatus(request.getStatus());
            return solicitudRepository.save(solicitud);
        } catch (Exception e) {
            throw new SolicitudNotExistsException();
        }
    }

}

