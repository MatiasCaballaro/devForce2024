package com.devforce.liceman.usuario.application;

import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.domain.repository.UserRepository;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    public final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::MapperToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(RuntimeException::new));
        return user.map(this::MapperToUserDTO);
    }

    @Override
    public UserResponseDTO updateOwnUser(UserRequestDTO userRequestDTO) {
        //TODO: Logica para que se verifique que el mail nuevo no exista anteriormente,
        // si existe el id en la BD debe concidir
        //TODO: Agregar el annotation para que sea unico el mail
        User loggedUser = getLoggedUser();
        UpdateUserData(userRequestDTO, loggedUser);
        return MapperToUserDTO(userRepository.save(loggedUser));
    }

    @Override
    public void deleteUserbyId(Long id) {
        userRepository.deleteById(id);
    }

    private void UpdateUserData(UserRequestDTO userRequestDTO, User user) {
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        if (userRequestDTO.getPassword() != null && !(userRequestDTO.getPassword().isEmpty())) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        user.setPhone(userRequestDTO.getPhone());
    }

    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }

    @Override
    public UserResponseDTO MapperToUserDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstname(user.getFirstname());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setHasTeams(user.getHasTeams());
        return userResponseDTO;
    }


}
