package com.example.configuration.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select() // 选择哪些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.example.controller")) // 对所有api进行监控
                // 配置仅适用了@ApiOperation注解的方法，被Swagger管理
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs 测试")
//                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
//                .termsOfServiceUrl("http://www.hmttommy.com")
//                .contact(new Contact("Tommy","http://www.hmttommy.com","tommyyaphetshu@163.com"))
                .version("v1.0")
                .build();
    }
}
