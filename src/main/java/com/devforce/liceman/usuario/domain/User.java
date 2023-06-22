package com.devforce.liceman.usuario.domain;

import com.devforce.liceman.security.domain.token.Token;
import com.devforce.liceman.solicitud.domain.Solicitud;
import com.devforce.liceman.usuario.domain.enums.Area;
import com.devforce.liceman.usuario.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"tokens", "solicitudes"})
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;

    private String lastname;

    private String password;

    private String phone;

    @Column(unique = true) // Asegura que el email no se repita
    private String email;


    @Column(name = "has_teams")
    private Boolean hasTeams;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens;


    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Solicitud> solicitudes = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return role.getAuthorities();
    }

    @Override
    public String getPassword () {
        return password;
    }

    @Override
    public String getUsername () {
        return email;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }
}
