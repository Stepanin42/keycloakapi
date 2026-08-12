package com.ipr.resource;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/api")
@RequiredArgsConstructor
public class SecuredResource {

    private final JsonWebToken jwt;

    private final SecurityIdentity identity;

    @GET
    @Path("/users")
    @RolesAllowed("user")
    @Operation(description = "Метод для пользователей под ролью user")
    public String forUsers() {
        return "hello " + jwt.getName();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @Operation(description = "Метод для пользователей под ролью admin")
    public String forAdmins() {
        return "roles: " + identity.getRoles();
    }
}
