package com.devforce.liceman.usuario.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),

    MENTOR_READ("mentor:read"),
    MENTOR_UPDATE("mentor:update"),
    MENTOR_CREATE("mentor:create"),
    MENTOR_DELETE("mentor:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    SOLICITUD_CREATE("solicitud:create"),
    SOLICITUD_UPDATE("solicitud:update"),
    SOLICITUD_READ("solicitud:read");

    @Getter
    private final String permission;
}
