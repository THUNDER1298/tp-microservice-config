package com.example.authorizationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    // Lire les configurations depuis le Config Server
    @Value("${security.productcomposite.admin.username}")
    private String adminUser;
    @Value("${security.productcomposite.admin.password}")
    private String adminPass;

    @Value("${security.productcomposite.user.username}")
    private String userUser;
    @Value("${security.productcomposite.user.password}")
    private String userPass;

    /**
     * Point d'entrée pour valider les identifiants.
     * La Gateway va appeler ce endpoint.
     */
    @PostMapping("/validate")
    public ResponseEntity<String> validate(
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestHeader("role") String role) {

        if (role.equals("ADMIN")) {
            if (username.equals(adminUser) && password.equals(adminPass)) {
                // C'est l'admin, il a le droit (lecture/écriture)
                return ResponseEntity.ok("ADMIN role validated");
            }
        } else if (role.equals("USER")) {
            if (username.equals(userUser) && password.equals(userPass)) {
                // C'est l'user, il a le droit (lecture seule)
                return ResponseEntity.ok("USER role validated");
            }
        }

        // Si rien ne correspond, l'accès est refusé
        return new ResponseEntity<>("Invalid credentials or role", HttpStatus.UNAUTHORIZED);
    }
}