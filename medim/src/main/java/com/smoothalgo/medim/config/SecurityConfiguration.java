package com.smoothalgo.medim.config;



import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@KeycloakConfiguration
@Import({SecurityProblemSupport.class, KeycloakSpringBootConfigResolver.class})
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;




    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;

        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/swagger-ui.html").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/keycloak-url").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/h2-console").permitAll()
            .antMatchers("/favicon.ico").permitAll()
            .antMatchers("/management/**").hasRole("ADMIN")
            //
            //.antMatchers("/api/**").authenticated()
            .antMatchers("/api/drug-covered-by-social-securities").authenticated()
            .antMatchers("/api/get-drug-covered-by-social-securities-by-name/{drugName}").authenticated()
            .antMatchers("/api/get-drug-covered-by-social-securities-by-id/{id}").hasRole("MANAGER")
            .antMatchers("/api/get-drug-covered-by-social-securities-by-codeCip/{codeCip}").authenticated()
            .antMatchers("/api/create-drug-covered-by-social-securities").hasRole("MANAGER")
            .antMatchers("/api/update-drug-covered-by-social-securities").hasRole("MANAGER")
            .antMatchers("/api/delete-drug-covered-by-social-securities-by-id/{id}").hasRole("MANAGER")
            .antMatchers("/api/delete-drug-covered-by-social-securities-by-CodeCip/{codeCip}").hasRole("MANAGER")
            .antMatchers("/**").permitAll();
        http.headers().frameOptions().disable();

    }
}
