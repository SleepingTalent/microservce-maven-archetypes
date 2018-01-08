#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String SECURITY_SCHEMA_OAUTH2 = "oauth2";

    @Value("${symbol_dollar}{security.oauth2.client.client-id}")
    private String clientId;

    @Value("${symbol_dollar}{security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${symbol_dollar}{security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Value("${symbol_dollar}{security.oauth2.client.grant-type}")
    private String grantType;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api/.*"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(newArrayList(oauth()));
    }


    @Bean
    public SecurityScheme oauth() {
        return new OAuthBuilder()
                .name(SECURITY_SCHEMA_OAUTH2)
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret,
                "realm", "swagger", "", ApiKeyVehicle.HEADER, "", " ");
    }

    private List<AuthorizationScope> scopes() {
      return newArrayList(new AuthorizationScope("openid", "write and read"));

//        return newArrayList(
//                new AuthorizationScope("write", "write and read"),
//                new AuthorizationScope("read", "read only"));
    }

    private List<GrantType> grantTypes() {
        GrantType grantType = new ClientCredentialsGrant(accessTokenUri);
        return newArrayList(grantType);
    }


    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Security Admin Service API",
                "API Creates/Finds/Assigns Subjects, Roles and Permissions",
                "1.0.0",
                "Terms of service",
                "emtdevelopment@babcockinternational.com",
                "License of API",
                "API license URL");
        return apiInfo;
    }

}
