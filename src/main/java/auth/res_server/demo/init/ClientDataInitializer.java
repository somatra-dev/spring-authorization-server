package auth.res_server.demo.init;

import auth.res_server.demo.domain.Client;
import auth.res_server.demo.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientDataInitializer implements CommandLineRunner {

    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository  clientRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultClients();
    }

    private void initializeDefaultClients() throws JsonProcessingException {
        Client existing = clientRepository.findByClientId("test-client").orElse(null);
        if (existing != null) {
            clientRepository.delete(existing);
            System.out.println("Deleted existing test-client");
        }

        // Create test client if it doesn't exist
        if (registeredClientRepository.findByClientId("test-client") == null) {
            RegisteredClient testClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("test-client")
                    .clientName("Test Client")
                    .clientSecret(passwordEncoder.encode("test-secret"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .redirectUri("http://127.0.0.1:8080/login/oauth2/code/test-client")
                    .redirectUri("http://127.0.0.1:8080/authorize")
                    .redirectUri("http://localhost:3000/oauth/callback")
                    .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                    .scope("openid")
                    .scope("profile")
                    .scope("email")
                    .scope("read")
                    .scope("write")
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(true)
                            .requireProofKey(true)
                            .build())
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                            .accessTokenTimeToLive(Duration.ofMinutes(30))
                            .refreshTokenTimeToLive(Duration.ofDays(3))
                            .reuseRefreshTokens(false)
                            .build())
                    .build();

            registeredClientRepository.save(testClient);
            System.out.println("Test client created: test-client");
        }

        clientRepository.findByClientId("m2m-client")
                .ifPresent(client -> {
                    clientRepository.delete(client);
                    System.out.println("Deleted existing custom client: m2m-client");
                });

        // === Machine-to-Machine Client (Client Credentials) ===
        if (registeredClientRepository.findByClientId("m2m-client") == null) {
            RegisteredClient m2mClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("m2m-client")
                    .clientName("M2M Service Client")
                    .clientSecret(passwordEncoder.encode("password")) // Change in prod!
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .scope("api.read")
                    .scope("api.write")
                    .scope("admin") // optional
                    // No redirect URIs needed for client_credentials
                    .clientSettings(ClientSettings.builder()
                            .requireAuthorizationConsent(false)  // No user consent
                            .requireProofKey(false)
                            .build())
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofHours(8))   // Long-lived for services
                            .reuseRefreshTokens(true)
                            .build())
                    .build();

            registeredClientRepository.save(m2mClient);
            System.out.println("Created M2M client: m2m-client");
            System.out.println("   Client ID: m2m-client");
            System.out.println("   Client Secret: super-secret-m2m-password");
            System.out.println("   Grant Type: client_credentials");
            System.out.println("   Scopes: api.read api.write admin");
        }

    }
}