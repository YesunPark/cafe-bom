package com.zerobase.cafebom.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.zerobase.cafebom"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .ignoredParameterTypes(Errors.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Cafe-Bom Api Test")
            .description("Cafe-Bom REST API입니다.<br>"
                + "<br>"
                + "권한이 필요한 기능은 회원가입, 로그인 API로 로그인 후 받은 토큰으로 테스트가 가능합니다. <br>"
                + "토큰 입력 방법 : **Bearer {받은 토큰}**  <br>"
                + "<br>"
                + "관리자 기능 : 토큰을 우측 초록색 Authorize 버튼에만 넣으면 됩니다.<br> "
                + "사용자 기능 : 토큰을 Authorize 버튼과 API 헤더 Authorize 두 군데에 넣어야합니다.<br>  ")
            .version("1.0.2")
            .contact(new Contact("Cafe-Bom GitHub", "https://github.com/YesunPark/cafe-bom", ""))
            .build();
    }

    // swagger-ui 페이지 연결 핸들러 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry
            .addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    // swagger Authorize 버튼 생성을 위한 기능
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }

    // swagger Authorize 버튼 생성을 위한 기능
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

    // swagger Authorize 버튼 생성을 위한 기능
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}