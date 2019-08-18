package com.mos.moskit.web;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.google.common.base.Predicate;
import com.mos.moskit.web.generic.ApiVersion;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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
		for (Integer apiVersions : findAllBeansApi()) {
			context.registerBean("Docket-v" + apiVersions, Docket.class, () -> createApiDoc("v" + apiVersions));
		}
	}

	private Set<Integer> findAllBeansApi() {
		Set<Integer> versions = new HashSet<>();
		Map<String, Object> restControllers = ac.getBeansWithAnnotation(RestController.class);
		for (Object restController : restControllers.values()) {
			Class<? extends Object> restControllerClass = restController.getClass();
			versions.addAll(getAllApiVersionFromClassHierarchy(restControllerClass));
		}
		return Collections.unmodifiableSet(new TreeSet<>(versions));
	}

	private Set<Integer> getAllApiVersionFromClassHierarchy(Class<? extends Object> restControllerClass) {
		Set<Integer> versions = new HashSet<>();
		versions.addAll(getAllApiVersionsFromClass(restControllerClass));
		if (!Object.class.equals(restControllerClass)) {
			versions.addAll(getAllApiVersionFromClassHierarchy(restControllerClass.getSuperclass()));
		}
		return versions;
	}

	private Set<Integer> getAllApiVersionsFromClass(Class<? extends Object> restControllerClass) {
		Set<Integer> versions = new HashSet<>();
		getVersions(AnnotationUtils.findAnnotation(restControllerClass, ApiVersion.class)).ifPresent(versions::addAll);
		for (Method method : restControllerClass.getDeclaredMethods()) {
			getVersions(AnnotationUtils.findAnnotation(method, ApiVersion.class)).ifPresent(versions::addAll);
		}
		return versions;
	}

	private Optional<List<Integer>> getVersions(ApiVersion anno) {
		//@formatter:off
		return Optional.ofNullable(anno)
			.map(ApiVersion::value)
			.map(ArrayUtils::toObject)
			.map(Arrays::asList);
		//@formatter:on
	}

	@Bean
	public Docket docket() {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
		.apiInfo(createApiInfo("*"))
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any())
		.build();
		//@formatter:on
	}

	private Docket createApiDoc(String version) {
		//@formatter:off
		String patternVersion = "/"+version+"/.*";
		String patternGeneric = "/(?!v\\d+).*";
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(createApiInfo(version))
				.groupName(version)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("^("+patternVersion+"|"+patternGeneric+")$"))
				.build();
		//@formatter:on
	}

	private ApiInfo createApiInfo(String version) {
		//@formatter:off
		return new ApiInfoBuilder()
				.title(String.format("Api %s Documentation", version))
				.version(version)
				.description("short description")
				.contact(new Contact("mmosor", "", "marmosmar@gmail.com"))
				.license("Apache 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.termsOfServiceUrl("terms of use")
				.termsOfServiceUrl("www.google.com")
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
