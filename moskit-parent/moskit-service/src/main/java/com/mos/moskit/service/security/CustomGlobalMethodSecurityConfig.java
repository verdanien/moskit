package com.mos.moskit.service.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class CustomGlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	@Override
	protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
		return new CustomSecurityAnnotationsProcessor(createAttributeFactory());
	}

	private ExpressionBasedAnnotationAttributeFactory createAttributeFactory() {
		return new ExpressionBasedAnnotationAttributeFactory(getExpressionHandler());
	}

//	@Override
//    public MethodSecurityMetadataSource methodSecurityMetadataSource() {
//        DelegatingMethodSecurityMetadataSource source = (DelegatingMethodSecurityMetadataSource) super.methodSecurityMetadataSource();
//        source.getMethodSecurityMetadataSources().add(new XX());
//        return source;
//    }
//    @Override
//    protected AccessDecisionManager accessDecisionManager() {
//        AffirmativeBased manager = (AffirmativeBased) super.accessDecisionManager();
//        manager.getDecisionVoters().add(new XX());
//        return manager;
//    }
}
