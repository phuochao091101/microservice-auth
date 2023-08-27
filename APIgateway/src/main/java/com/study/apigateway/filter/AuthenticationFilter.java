package com.study.apigateway.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class  AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

        @Autowired
    private RestTemplate template;


    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String authToken="";
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authToken = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
//                    jwtUtil.validateToken(authHeader);
                    String identityServiceUrl="http://localhost:8222/auth/validate-token";
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + authToken);

                    ResponseEntity<String> response = template.exchange(
                            identityServiceUrl,
                            HttpMethod.GET,
                            null,
                            String.class,
                            headers
                    );

                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println(response.getBody());
                    } else {
                        // Handle error response
                        throw new RuntimeException("un authorized access to application");
                    }

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
