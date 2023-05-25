//package com.sunkyuj.douner.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig { // springfox는 스프링부트 3 이상에서 안됨
//
//    @Bean
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("douner")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.sunkyuj.douner"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("douner")
//                .description("사회적 약자를 위한 실시간 도움 서비스 API 명세서")
//                .version("1.0.0")
//                .build();
//    }
//}