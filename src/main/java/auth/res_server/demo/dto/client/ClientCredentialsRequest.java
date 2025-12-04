package auth.res_server.demo.dto.client;

import lombok.Data;

@Data
public class ClientCredentialsRequest extends CreateClient {
    private Integer accessTokenTTLHours = 8;
    private Boolean reuseRefreshTokens = true;
}