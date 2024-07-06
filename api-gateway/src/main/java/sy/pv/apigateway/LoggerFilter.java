package sy.pv.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
@Component
public class LoggerFilter implements GlobalFilter {
    private static final Logger LOGGER_FILTER = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI requestUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String method = exchange.getRequest().getMethod().toString();
        HttpHeaders headers = exchange.getRequest().getHeaders();
        LOGGER_FILTER.info("Incoming request: {} - {} - Headers: {}", method, requestUri, headers);
        return chain.filter(exchange);
    }
}
