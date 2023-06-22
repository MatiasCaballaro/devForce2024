package com.devforce.liceman.usuario.application;

import com.devforce.liceman.security.application.JwtService;
import com.devforce.liceman.security.domain.token.Token;
import com.devforce.liceman.security.domain.token.TokenJPARepository;
import com.devforce.liceman.security.domain.token.TokenType;
import com.devforce.liceman.security.infrastructure.dto.AuthenticationResponse;
import com.devforce.liceman.shared.application.MapperUtils;
import com.devforce.liceman.shared.application.UserUtils;
import com.devforce.liceman.shared.exceptions.EmailAlreadyExistsException;
import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.domain.enums.Role;
import com.devforce.liceman.usuario.domain.repository.UserRepository;
import com.devforce.liceman.usuario.infrastructure.dto.UserRequestDTO;
import com.devforce.liceman.usuario.infrastructure.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenJPARepository tokenJPARepository;

    private final UserUtils userUtils;

    private final MapperUtils mapperUtils;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    @Override
    public AuthenticationResponse createUser (UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        if(request.getRole()==Role.MENTOR && request.getArea()==null || request.getRole()!=Role.MENTOR && request.getArea()!=null){
            throw new IllegalArgumentException();
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .hasTeams(request.getHasTeams())
                .build();
        if(request.getArea()!=null){
            user.setArea(request.getArea());
        }
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken (User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenJPARepository.save(token);
    }


    @Override
    public List<UserResponseDTO> findAllUsers () {
        return userRepository.findAll()
                .stream()
                .map(mapperUtils::MapperToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> getUserById (Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(RuntimeException::new));
        return user.map(mapperUtils::MapperToUserDTO);
    }

    //El método UpdateOwnUser no involucra el cambio de mail
    @Override
    public UserResponseDTO updateOwnUser (UserRequestDTO userRequestDTO) {
        User loggedUser = userUtils.getLoggedUser();
        UpdateUserData(userRequestDTO, loggedUser);
        return mapperUtils.MapperToUserDTO(userRepository.save(loggedUser));
    }

    private void UpdateUserData (UserRequestDTO userRequestDTO, User user) {
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        if (userRequestDTO.getPassword() != null && !(userRequestDTO.getPassword().isEmpty())) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }
        user.setPhone(userRequestDTO.getPhone());
        user.setHasTeams(userRequestDTO.getHasTeams());
    }

    @Override
    public void deleteUserbyId (Long id) {
        userRepository.deleteById(id);
    }


}
