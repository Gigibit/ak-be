package com.ak.be.engine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@EnableTransactionManagement
@SpringBootApplication
//@ConfigurationProprties
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
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().withUser("ian").password("{noop}ian").roles("USER", "ADMIN")
        auth.inMemoryAuthentication().withUser("dan").password("{noop}dan").roles("USER")
        auth.inMemoryAuthentication().withUser("chris").password("{noop}chris").roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        super.configure(http?.csrf()?.disable())
    }

}