package sy.pv.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@RestController
//@EnableConfigurationProperties(HttpClient.UriConfiguration.class)
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://httpbin.org:80"))
//				.build();
//	}
}
//@ConfigurationProperties
//class UriConfiguration {
//
//	private String httpbin = "http://httpbin.org:80";
//
//	public String getHttpbin() {
//		return httpbin;
//	}
//
//	public void setHttpbin(String httpbin) {
//		this.httpbin = httpbin;
//	}
//}