package portfolio.projects.chillblog.web;

import portfolio.projects.chillblog.model.Credentials;
import portfolio.projects.chillblog.model.JwtResponse;
import portfolio.projects.chillblog.model.User;
import portfolio.projects.chillblog.service.UserService;
import portfolio.projects.chillblog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<JwtResponse> login(@RequestBody Credentials credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        final User user = userService
                .getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        log.info("Login successful for {}: {}", user.getUsername(), token);
        return ResponseEntity.ok(new JwtResponse(token, user));
    }

}
