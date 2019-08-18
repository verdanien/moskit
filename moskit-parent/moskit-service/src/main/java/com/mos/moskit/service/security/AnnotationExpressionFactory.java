package com.mos.moskit.service.security;

import static java.util.Arrays.stream;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mos.moskit.domain.entity.account.Role;
import com.mos.moskit.service.security.HasAuthority.HasAuthorities;
import com.mos.moskit.service.security.HasRole.HasRoles;

/**
 * @see https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/el-access.html
 * @author mmosor
 * 
 *         <table summary="Common built-in expressions" style="border-collapse:
 *         collapse;border-top: 0.5pt solid ; border-bottom: 0.5pt solid ;
 *         border-left: 0.5pt solid ; border-right: 0.5pt solid ; ">
 *         <colgroup><col class="col_1"><col class="col_2"></colgroup><thead>
 *         <tr>
 *         <th style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">Expression</th>
 *         <th style="border-bottom: 0.5pt solid ; " align="left" valign=
 *         "top">Description</th>
 *         </tr>
 *         </thead><tbody>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">hasRole([role])</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the current principal
 *         has the specified role. By default if the supplied role does not
 *         start with 'ROLE_' it will be added. This can be customized by
 *         modifying the <code class="literal">defaultRolePrefix</code> on
 *         <code class="literal">DefaultWebSecurityExpressionHandler</code>.
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">hasAnyRole([role1,role2])</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the current principal
 *         has any of the supplied roles (given as a comma-separated list of
 *         strings). By default if the supplied role does not start with 'ROLE_'
 *         it will be added. This can be customized by modifying the
 *         <code class="literal">defaultRolePrefix</code> on
 *         <code class="literal">DefaultWebSecurityExpressionHandler</code>.
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">hasAuthority([authority])</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the current principal
 *         has the specified authority.
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">hasAnyAuthority([authority1,authority2])</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the current principal
 *         has any of the supplied roles (given as a comma-separated list of
 *         strings)
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">principal</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Allows direct access to the principal object representing the current
 *         user
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">authentication</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Allows direct access to the current
 *         <code class="literal">Authentication</code> object obtained from the
 *         <code class="literal">SecurityContext</code>
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">permitAll</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Always evaluates to <code class="literal">true</code>
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">denyAll</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Always evaluates to <code class="literal">false</code>
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">isAnonymous()</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the current principal is
 *         an anonymous user
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">isRememberMe()</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the current principal is
 *         a remember-me user
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">isAuthenticated()</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the user is not
 *         anonymous
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class="literal">isFullyAuthenticated()</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the user is not an
 *         anonymous or a remember-me user
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; border-bottom: 0.5pt solid ; "
 *         align="left" valign="top">
 *         <p>
 *         <code class=
 *         "literal">hasPermission(Object target, Object permission)</code>
 *         </p>
 *         </td>
 *         <td style="border-bottom: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the user has access to
 *         the provided target for the given permission. For example,
 *         <code class="literal">hasPermission(domainObject, 'read')</code>
 *         </p>
 *         </td>
 *         </tr>
 *         <tr>
 *         <td style="border-right: 0.5pt solid ; " align="left" valign="top">
 *         <p>
 *         <code class=
 *         "literal">hasPermission(Object targetId, String targetType, Object permission)</code>
 *         </p>
 *         </td>
 *         <td style="" align="left" valign="top">
 *         <p>
 *         Returns <code class="literal">true</code> if the user has access to
 *         the provided target for the given permission. For example,
 *         <code class=
 *         "literal">hasPermission(1, 'com.example.domain.Message', 'read')</code>
 *         </p>
 *         </td>
 *         </tr>
 *         </tbody>
 *         </table>
 *
 */
public class AnnotationExpressionFactory {
	private final Map<Class<? extends Annotation>, Function<? extends Annotation, String>> map = new HashMap<>();

	public static AnnotationExpressionFactory getInstance() {
		return AnnotationExpressionFactoryHolder.INSTANCE;
	}

	public AnnotationExpressionFactory() {
		register(HasRole.class, it -> "hasRole(" + literal(it.value().name()) + ")");
		register(HasRoles.class, it -> and("hasRole(%s)", it.value(), s -> s.map(HasRole::value).map(Role::name)));
		register(HasAnyRole.class, it -> "hasAnyRole(" + literals(it.value(), s -> s.map(Role::name)) + ")");
		register(HasAuthority.class, it -> "hasAuthority(" + literal(it.value()) + ")");
		register(HasAuthorities.class, it -> and("hasAuthority(%s)", it.value(), s -> s.map(HasAuthority::value)));
		register(HasAnyAuthority.class, it -> "hasAnyAuthority(" + literals(it.value(), s -> s) + ")");
	}

	private static final String literal(String value) {
		return "'" + value + "'";
	}

	private static final <V> String and(String template, V[] array, Function<Stream<V>, Stream<String>> map) {
		return map.apply(stream(array)).map(AnnotationExpressionFactory::literal).map(it -> String.format(template, it)).collect(Collectors.joining(" and "));
	}

	private static final <V> String literals(V[] array, Function<Stream<V>, Stream<String>> map) {
		return map.apply(stream(array)).map(AnnotationExpressionFactory::literal).collect(Collectors.joining(","));
	}

	public static String expression(Annotation annotation) {
		return getInstance().getExpression(annotation);
	}

	protected <ANNOTATION extends Annotation> void register(Class<ANNOTATION> annotation, Function<ANNOTATION, String> expressionBuilder) {
		map.put(annotation, expressionBuilder);
	}

	public String getExpression(Annotation annotation) {
		@SuppressWarnings("unchecked")
		Function<Annotation, String> expressionBuilder = (Function<Annotation, String>) map.get(getType(annotation));
		if (expressionBuilder == null) {
			throw new IllegalArgumentException(String.format("Annotation [%s] is not supported!", annotation));
		}
		return expressionBuilder.apply(annotation);
	}

	private Class<? extends Annotation> getType(Annotation annotation) {
		return annotation.annotationType();
	}

	private static final class AnnotationExpressionFactoryHolder {
		private static final AnnotationExpressionFactory INSTANCE = new AnnotationExpressionFactory();
	}

	public static Set<Class<? extends Annotation>> annotations() {
		return getInstance().map.keySet();
	}
}
