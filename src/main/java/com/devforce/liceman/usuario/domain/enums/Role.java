package com.devforce.liceman.usuario.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.devforce.liceman.usuario.domain.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    SOLICITUD_CREATE,
                    SOLICITUD_READ,
                    SOLICITUD_UPDATE

            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MENTOR_READ,
                    MENTOR_UPDATE,
                    MENTOR_DELETE,
                    MENTOR_CREATE,
                    USER_READ,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE,
                    SOLICITUD_UPDATE,
                    SOLICITUD_READ
            )
    ),
    MENTOR(
            Set.of(
                    MENTOR_READ,
                    MENTOR_UPDATE,
                    MENTOR_DELETE,
                    MENTOR_CREATE,
                    SOLICITUD_CREATE,
                    SOLICITUD_READ,
                    SOLICITUD_UPDATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities () {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
