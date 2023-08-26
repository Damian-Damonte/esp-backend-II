package com.example.apipersona.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> realAccessRoles = (Map<String, Object>) source.getClaims().get("realm_access");

        if (realAccessRoles != null && !realAccessRoles.isEmpty()) {
            authorities.addAll(extractRoles(realAccessRoles));
        }

        return new JwtAuthenticationToken(source, authorities);
    }

    private static Collection<GrantedAuthority> extractRoles(Map<String, Object> realmAccessRoles) {
        return ((List<String>) realmAccessRoles.get("roles"))
                .stream().map(roleMap -> "ROLE_" + roleMap)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

