package com.mos.moskit.service.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;
import org.springframework.util.ClassUtils;

public class CustomSecurityAnnotationsProcessor extends AbstractMethodSecurityMetadataSource {
	private final PrePostInvocationAttributeFactory attributeFactory;

	public CustomSecurityAnnotationsProcessor(PrePostInvocationAttributeFactory attributeFactory) {
		this.attributeFactory = attributeFactory;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
		if (method.getDeclaringClass() == Object.class) {
			return Collections.emptyList();
		}else {
			//TODO - get annotations from targetClass
		}

		StringJoiner expression = new StringJoiner(" and ");
		for (Class<? extends Annotation> annotationType : AnnotationExpressionFactory.annotations()) {
			Annotation annotation = findAnnotation(method, targetClass, annotationType); //TODO - SHOULD FIND ALL ANNOTATIONS WITH SAME TYPE NOT ONLY FIRST ONE!
			if (annotation != null) {
				expression.add(AnnotationExpressionFactory.expression(annotation));
			}
		}

		return createConfig(expression);
	}

	private Collection<ConfigAttribute> createConfig(StringJoiner expression) {
		List<ConfigAttribute> attrs = new LinkedList<>();
		String finalExpression = expression.toString();
		if (StringUtils.isNotEmpty(finalExpression)) {
			PreInvocationAttribute pre = attributeFactory.createPreInvocationAttribute(null, null, expression.toString());
			if (pre != null) {
				attrs.add(pre);
			}
		}
		return attrs;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	private <A extends Annotation> A findAnnotation(Method method, Class<?> targetClass, Class<A> annotationClass) {
		// The method may be on an interface, but we need attributes from the target
		// class.
		// If the target class is null, the method will be unchanged.
		Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
		A annotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass);

		if (annotation != null) {
			logger.debug(annotation + " found on specific method: " + specificMethod);
			return annotation;
		}

		// Check the original (e.g. interface) method
		if (specificMethod != method) {
			annotation = AnnotationUtils.findAnnotation(method, annotationClass);

			if (annotation != null) {
				logger.debug(annotation + " found on: " + method);
				return annotation;
			}
		}

		// Check the class-level (note declaringClass, not targetClass, which may not
		// actually implement the method)
		annotation = AnnotationUtils.findAnnotation(specificMethod.getDeclaringClass(), annotationClass);

		if (annotation != null) {
			logger.debug(annotation + " found on: " + specificMethod.getDeclaringClass().getName());
			return annotation;
		}

		return null;
	}
}
