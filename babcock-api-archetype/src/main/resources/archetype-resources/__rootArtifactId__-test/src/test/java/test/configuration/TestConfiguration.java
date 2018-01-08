#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(value = {"${package}.test"})
@PropertySource("classpath:test.properties")
public class TestConfiguration {

    @Value("${symbol_dollar}{security.auth.id}")
    private String authId;

    @Value("${symbol_dollar}{security.auth.client.id}")
    private String authClientId;

    @Value("${symbol_dollar}{security.auth.client.secret}")
    private String authClientSecret;

    @Value("${symbol_dollar}{security.auth.grant.type}")
    private String grantType;

    @Value("${symbol_dollar}{security.auth.token.url}")
    private String tokenUrl;

    @Value("${symbol_dollar}{spring.datasource.url}")
    private String datasourceUrl;

    @Value("${symbol_dollar}{spring.datasource.username}")
    private String datasourceUser;

    @Value("${symbol_dollar}{spring.datasource.password}")
    private String datasourcePassword;

    @Value("${symbol_dollar}{spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "oauthRestTemplate")
    public RestTemplate oauthRestTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();

        resourceDetails.setId(authId);
        resourceDetails.setClientId(authClientId);
        resourceDetails.setClientSecret(authClientSecret);
        resourceDetails.setAccessTokenUri(tokenUrl);
        resourceDetails.setGrantType(grantType);

        DefaultOAuth2ClientContext context = new DefaultOAuth2ClientContext();
        return new OAuth2RestTemplate(resourceDetails,context);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(datasourceDriver);
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(datasourceUser);
        dataSource.setPassword(datasourcePassword);

        return new JdbcTemplate(dataSource);
    }

}