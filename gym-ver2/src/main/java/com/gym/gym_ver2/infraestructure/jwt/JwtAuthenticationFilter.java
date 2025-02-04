package com.gym.gym_ver2.infraestructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //atributos
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    //constructor
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override// se ejecuta en cada peticion
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Ejecutando filtro JWT");

        //obtener token
        final String token = getTokenFromRequest(request);
        System.out.println("Token recibido: " + token);
        final String userEmail;
        //validar si el token es nulo
        if (token == null) {
            System.out.println("Token no encontrado en la solicitud");
            filterChain.doFilter(request, response);
            return;
        }
        //extraer el correo del token
        userEmail = jwtService.extractUsername(token);
        System.out.println("Correo del token: " + userEmail);
        //validar token y correo
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            System.out.println("UserDetails cargado: " + userDetails.getUsername());
            try {
                if (jwtService.validateToken(token, userDetails)) {
                    Claims claims = Jwts.parserBuilder()// Construir el token mediante la librería Jwts
                            .setSigningKey(jwtService.getKey()) // Clave secreta usada para firmar
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String roles = claims.get("rol", String.class); // Obtener roles como String
                    System.out.println("Roles del token: " + roles);
                    // Convertir roles en una lista de autoridades
                    List<GrantedAuthority> authorities = (roles != null) ?
                            Collections.singletonList(new SimpleGrantedAuthority(roles)) : List.of();
//                            Arrays.stream(roles.split(","))
//                                    .map(SimpleGrantedAuthority::new)
//                                    .collect(Collectors.toList()) : List.of();

                    System.out.println("Roles del token fitro: " + roles);
                    System.out.println("Autoridades generadas filtro: " + authorities);
// Validar que el usuario tenga el rol ROLE_Administrador si accede a rutas específicas
                    if (request.getRequestURI().startsWith("/user/")) {
                        boolean esAdministrador = authorities.stream()
                                .anyMatch(auth -> auth.getAuthority().equals("ROLE_Administrador"));

                        if (!esAdministrador) {
                            System.err.println("Acceso denegado: Se requiere rol de Administrador");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado: Se requiere rol de Administrador");
                            return;
                        }
                    }
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Autenticación establecida en el SecurityContextHolder");
                    // Validar que el usuario tenga el rol "Superusuario"**
//                    boolean esSuperusuario = authorities.stream()
//                            .anyMatch(auth -> auth.getAuthority().equals("ROLE_Superusuario"));
//
//                    if (!esSuperusuario) {
//                        System.err.println("Acceso denegado: No es Superusuario");
//                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado: Se requiere rol de Superusuario");
//                        return;
//                    }
                    // Configurar la autenticación en el contexto de seguridad

                   // authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                }
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                System.err.println("El token ha expirado: " + e.getMessage());
            } catch (io.jsonwebtoken.SignatureException e) {
                System.err.println("Firma del token no válida: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error al validar el token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);//obtener el item de autenticacion
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);//el token se encuentra despues de la palabra Bearer
        }
        return null;
    }

}