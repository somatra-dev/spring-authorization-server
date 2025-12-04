package auth.res_server.demo.controller;


import auth.res_server.demo.domain.Client;
import auth.res_server.demo.dto.client.AuthorizationCodeRequest;
import auth.res_server.demo.dto.client.ClientCredentialsRequest;
import auth.res_server.demo.dto.client.ResponseClient;
import auth.res_server.demo.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Create PKCE Client
    @PostMapping("/pkce")
    public ResponseEntity<ResponseClient> createPKCEClient(
            @Valid @RequestBody AuthorizationCodeRequest request) {
        request.setAuthCodeType("pkce");
        request.setRequireProofKey(true); // Force PKCE
        ResponseClient response = clientService.createAuthorizationCodeClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Create Normal Authorization Code Client
    @PostMapping("/normal-auth")
    public ResponseEntity<ResponseClient> createNormalAuthClient(
            @Valid @RequestBody AuthorizationCodeRequest request) {
        request.setAuthCodeType("normal");
        ResponseClient response = clientService.createAuthorizationCodeClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Create Client Credentials Client
    @PostMapping("/client-credentials")
    public ResponseEntity<ResponseClient> createClientCredentialsClient(
            @Valid @RequestBody ClientCredentialsRequest request) {
        ResponseClient response = clientService.createClientCredentialsClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}