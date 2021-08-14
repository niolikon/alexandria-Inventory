package org.niolikon.alexandria.inventory.system;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ServiceSwaggerConfig {

    // URL Swagger UI: http://localhost:8080/inventory/swagger-ui.html
    // URL Swagger: http://localhost:8080/inventory/v2/api-docs
    // Docs: https://swagger.io/docs/
    
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                    .apis(RequestHandlerSelectors.basePackage("org.niolikon.alexandria.inventory"))
                    .paths(regex("/.*"))
                .build();
    }
    
    private ApiInfo apiInfo() 
    {
        return new ApiInfoBuilder()
                .title("Alexandria-Inventory")
                .description("A RESTFul service for the management of an Inventory")
                .version("0.0.0-A08")
                .license("MIT License")
                .licenseUrl("https://github.com/niolikon/alexandria-Inventory/blob/main/LICENSE")
                .contact(new Contact("Simone Andrea Muscas",  "https://www.linkedin.com/in/simoneandreamuscas/", 
                        "simoneandrea.muscas@gmail.com"))
                .build();
    }
    
    private ApiKey apiKey() { 
        return new ApiKey("JWT", "Authorization", "header"); 
    }
    
    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 

    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }
}
