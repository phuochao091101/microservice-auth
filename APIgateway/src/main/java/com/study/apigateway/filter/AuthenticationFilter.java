package com.study.apigateway.filter;


import com.study.apigateway.exception.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
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
                    throw new SecurityException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String authToken="";
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authToken = authHeader.substring(7);
                    System.out.println("Token: "+authToken);
                }
                try {
//                    //REST call to AUTH service
                    template.getForObject("http://localhost:8222/auth/validate-token?token=" + authToken, String.class);
//                    ResponseEntity<String> response = template.getForEntity("http://localhost:8222/auth/validate-token?token=" + authToken, String.class, 1);
//                    if(response.getStatusCode() == HttpStatus.OK) {
//                        System.out.println( response.getBody());
//                    } else {
//                        System.out.println("invalid access...!");
//                        throw new SecurityException("Access Denied !");
//                    }

                } catch (HttpStatusCodeException ex) {
                    System.out.println(ex.getStatusCode().toString());
                    throw new SecurityException("Access Denied !");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
