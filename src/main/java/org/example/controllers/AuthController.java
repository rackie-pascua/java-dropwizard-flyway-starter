package org.example.controllers;

import io.jsonwebtoken.Jwts;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import org.example.models.LoginRequest;
import org.example.services.AuthService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Auth API")
@Path("/api/auth")
@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                apiKeyAuthDefinitions = {
                        @ApiKeyAuthDefinition(
                                key = HttpHeaders.AUTHORIZATION,
                                name = HttpHeaders.AUTHORIZATION,
                                in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER
                        )
                }
        )
)
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        try {
            return Response.ok().entity(authService.login(loginRequest)).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();

        }
    }

//    @POST
//    @Path("/register")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({})
}
