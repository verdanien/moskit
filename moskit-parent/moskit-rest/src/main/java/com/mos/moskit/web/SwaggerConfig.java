package com.mos.moskit.web;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.google.common.base.Predicate;
import com.mos.moskit.web.generic.ApiVersion;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private ApplicationContext ac;
	@Autowired
	private GenericWebApplicationContext context;

	@PostConstruct
	private void initSwagger() {
		for (int i = 1; i < 10; i++) {
			final int id = i;
			context.registerBean("Docket-v" + id, Docket.class, () -> createApiDoc("v" + id));
		}
	}

	@Bean
	public Docket docket() {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
		.apiInfo(new ApiInfoBuilder()
				.title(String.format("Api Documentation"))
				.description("short description")
				.contact(new Contact("mmosor", "", "marmosmar@gmail.com"))
				.license("Apache 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.termsOfServiceUrl("terms of use")
				.termsOfServiceUrl("www.google.com")
				.build())
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any())
		.build();
		//@formatter:on
	}

	private Docket createApiDoc(String version) {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title(String.format("Api %s Documentation", version))
						.version(version)
						.description("short description")
						.contact(new Contact("mmosor", "", "marmosmar@gmail.com"))
						.license("Apache 2.0")
						.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
						.termsOfServiceUrl("terms of use")
						.termsOfServiceUrl("www.google.com")
						.build())
//				.host("xx")
				.groupName(version)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/"+version+"/**"))
				.build();
		//@formatter:on
	}

	static class RequestHandlerSelectorsExt {
		public static Predicate<RequestHandler> withInterface() {
			return new Predicate<RequestHandler>() {
				@Override
				public boolean apply(RequestHandler input) {
					ApiVersion ver = input.findAnnotation(ApiVersion.class).orNull();
					return ver == null || Arrays.asList(ver.value()).contains(1);
//					return declaringClass(input) == EndpointMvcAdapter.class;
				}
			};
		}

		private static Class<?> declaringClass(RequestHandler input) {
			return input.getHandlerMethod().getMethod().getDeclaringClass();
		}
	}
}
