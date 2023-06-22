package com.devforce.liceman.usuario.application;

import com.devforce.liceman.security.infrastructure.dto.AuthenticationResponse;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthenticationResponse createUser (UserRequestDTO userRequest);

    List<UserResponseDTO> findAllUsers ();

    Optional<UserResponseDTO> getUserById (Long id);

    UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO);

    void deleteUserbyId (Long id);


}
