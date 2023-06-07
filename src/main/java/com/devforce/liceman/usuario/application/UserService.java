package com.devforce.liceman.usuario.application;

import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {


    List<UserResponseDTO> findAllUsers ();

    Optional<UserResponseDTO> getUserById (Long id);

    UserResponseDTO updateOwnUser(UserRequestDTO userRequestDTO);

    void deleteUserbyId (Long id);

    UserResponseDTO MapperToUserDTO (User user);
}
