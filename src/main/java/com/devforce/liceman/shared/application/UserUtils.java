package com.devforce.liceman.shared.application;

import com.devforce.liceman.usuario.domain.User;
import com.devforce.liceman.usuario.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtils {

    public final UserRepository userRepository;

    public User getLoggedUser () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow();
    }
}
