package com.secondave.config

//
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.{DecodedJWT, JWTVerifier}
import jakarta.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@Component
@EnableWebSecurity
class SecurityConfig() {

  @Bean
  def filterChain(http: HttpSecurity): DefaultSecurityFilterChain = {
   http.authorizeHttpRequests()
      .requestMatchers("/").permitAll()
      .anyRequest().authenticated()
      .and().cors()
      .and().oauth2ResourceServer().jwt()
    http.build()
  }
}