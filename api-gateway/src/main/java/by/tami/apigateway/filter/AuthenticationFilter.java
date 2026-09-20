package by.tami.apigateway.filter;

import by.tami.apigateway.exception.BadRequestException;
import by.tami.apigateway.util.JwtUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(@NonNull Config config) {
        return ((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsHeader(HttpHeaders.AUTHORIZATION)) {
                throw new BadRequestException("Missing Authorization Header");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
            if (authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }


            if (!jwtUtil.validateToken(authHeader)) {
                throw new BadRequestException("Token is not valid");
            }

            String userId = jwtUtil.extractUserId(authHeader);
            String role = jwtUtil.extractClaim(authHeader, claims -> claims.get("role", String.class));

            var mutatedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-User-Id", userId)
                            .header("X-User-Role", role)
                            .build())
                    .build();

            return chain.filter(mutatedExchange);
        });
    }

    public static class Config {

    }
}
