package cn.szkedun.config;

import cn.szkedun.handler.CustomizeAuthenticationFailureHandler;
import cn.szkedun.handler.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EnableWebSecurity
@Order(100)
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Autowired
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .anyRequest().authenticated())
                .formLogin(formLoginCustomizer -> formLoginCustomizer
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(customizeAuthenticationFailureHandler))
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer.successHandler(oAuth2AuthenticationSuccessHandler));
    }
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }

}