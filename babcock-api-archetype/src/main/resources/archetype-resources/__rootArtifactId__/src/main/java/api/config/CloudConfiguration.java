#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableCircuitBreaker
@ComponentScan("${package}.api")
@Import({SecurityConfiguration.class,DatabaseConfiguration.class, SwaggerConfiguration.class})
public class CloudConfiguration {
}
