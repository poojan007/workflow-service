package bt.gov.oag.activiti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.description("OAG Workflow Service API")
						.title("OAG Workflow Service API")
						.version("1.0.0")
						.build())
				.enable(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("bt.gov.oag.activiti.controller"))
				.paths(PathSelectors.any())
				.build();
	}
}
