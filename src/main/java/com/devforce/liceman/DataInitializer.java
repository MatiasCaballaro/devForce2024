package com.devforce.liceman;

import com.devforce.liceman.security.application.AuthenticationService;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.devforce.liceman.usuario.domain.enums.Role.*;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${sample.data}")
    private Boolean sampleData;
    private final AuthenticationService authenticationService;

    public DataInitializer(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void run(String... args) {


        if (sampleData) {

            System.out.println("Test Data Initializer begins!  ");

            var user = UserRequestDTO.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user@mail.com")
                    .password("password")
                    .role(USER)
                    .hasTeams(true)
                    .build();
            System.out.println("User token: " + authenticationService.register(user).getAccessToken());


            var mentor = UserRequestDTO.builder()
                    .firstname("Mentor")
                    .lastname("Mentor")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MENTOR)
                    .hasTeams(true)
                    .build();
            System.out.println("Mentor token: " + authenticationService.register(mentor).getAccessToken());

            var admin = UserRequestDTO.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .hasTeams(true)
                    .build();
            System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());

            System.out.println("Test Data Initializer finished! ");
        }
    }

}

