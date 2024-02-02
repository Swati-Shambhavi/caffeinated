package com.caffeinated.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> resourceAccess = (Map<String, Object>) source.getClaims().get("resource_access");
        String clientId = (String) source.getClaims().get("azp");
        if(resourceAccess == null || resourceAccess.isEmpty() || !resourceAccess.containsKey(clientId)){
            return new ArrayList<>();
        }
        List<String> clientRoles = extractClientRolesFromJwt(resourceAccess, clientId);
        Collection<GrantedAuthority> roles = clientRoles.stream().map("ROLE_"::concat).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return roles;
    }

    @SuppressWarnings("unchecked")
    private static List<String> extractClientRolesFromJwt(Map<String, Object> resourceAccess, String clientId) {
        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
        return clientAccess.containsKey("roles")? (List<String>) clientAccess.get("roles") : new ArrayList<>();
    }
}
