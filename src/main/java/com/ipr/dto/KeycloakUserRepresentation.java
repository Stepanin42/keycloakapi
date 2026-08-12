package com.ipr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class KeycloakUserRepresentation {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private boolean enabled;

    private List<Credential> credentials;
}
