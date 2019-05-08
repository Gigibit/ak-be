package com.ak.be.engine

import com.ak.be.engine.service.user.impl.UserServiceDefault
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import javax.sql.DataSource


@EnableTransactionManagement
@SpringBootApplication
//@ConfigurationProprties
@EnableAsync
class EngineApplication

fun main(args: Array<String>) {
    runApplication<EngineApplication>(*args)
}

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/app")
//        config.enableSimpleBroker("/topic", "/queue")
//        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        //root
//        registry.addEndpoint("/greeting").withSockJS()
        registry.addEndpoint("/ak-websocket").withSockJS()
    }
}


@Configuration

@EnableResourceServer

class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    val resourceId = "resource-server-rest-api"

    val securedReadScope = "#oauth2.hasScope('read')"

    val securedWriteScope = "#oauth2.hasScope('write')"

    val securedPattern = "/**"

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(resourceId)
    }

    override fun configure(http: HttpSecurity) {
        http.requestMatchers()
                .antMatchers(securedPattern)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, securedPattern)
                .access(securedWriteScope)//"#oauth2.hasScope('write')"
                .anyRequest().access(securedReadScope)
    }

}


@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired val userService: UserServiceDefault) : WebSecurityConfigurerAdapter() {

//    @Autowired
//    @Throws(Exception::class)
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth.inMemoryAuthentication().withUser("ian").password("{noop}ian").roles("USER", "ADMIN")
//        auth.inMemoryAuthentication().withUser("dan").password("{noop}dan").roles("USER")
//        auth.inMemoryAuthentication().withUser("chris").password("{noop}chris").roles("USER")
//    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return UserDetailsService(userService::createUserDetails)
    }

    override fun configure(http: HttpSecurity) {
        super.configure(http.csrf().disable())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService()).passwordEncoder(BCryptPasswordEncoder(8))
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AuthServerOAuth2Config(@Autowired val userDetailsService: UserDetailsService,
                             @Autowired val dataSource: DataSource,
                             @Autowired val authenticationManager: AuthenticationManager) : AuthorizationServerConfigurerAdapter() {
    @Bean
    fun oauthAccessDeniedHandler(): OAuth2AccessDeniedHandler {
        return OAuth2AccessDeniedHandler()
    }

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(BCryptPasswordEncoder(4))
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(JdbcTokenStore(dataSource))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
    }

}