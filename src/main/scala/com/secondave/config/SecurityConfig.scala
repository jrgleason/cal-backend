package com.secondave.config

//
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
//
//
@Component
@EnableWebSecurity
class SecurityConfig(){
  @Bean
  def filterChain(http: HttpSecurity): DefaultSecurityFilterChain = {
    http
      .authorizeHttpRequests()
      .requestMatchers(request => request.getRequestURI == "/" || request.getRequestURI == "/public").permitAll()
      .and()
      .build()
  }
//
//  override def configure(http: HttpSecurity): Unit = {
//    http
//      .addFilterBefore(new Auth0Filter(auth0Config), classOf[AbstractPreAuthenticatedProcessingFilter])
//      .authorizeRequests()
//      .requestMatchers(request => request.getRequestURI == "/" || request.getRequestURI == "/public").permitAll()
//      .requestMatchers(request => request.getRequestURI.startsWith("/api/")).authenticated()
//      .and()
//      .oauth2ResourceServer()
//      .jwt()
//  }
//
}
//
//class Auth0Filter(val auth0Config: Auth0Config) extends AbstractPreAuthenticatedProcessingFilter {
//
//  override def getPreAuthenticatedPrincipal(request: HttpServletRequest): AnyRef = {
//    auth0Config.validateJwt(request.getHeader("Authorization"))
//    // If the JWT is valid, return the authenticated user principal
//    // Here, you can retrieve the user information from the JWT claims
//    // and return a custom principal object representing the authenticated user
//  }
//
//  override def getPreAuthenticatedCredentials(request: HttpServletRequest): AnyRef = {
//    // This method should return a credentials object, which is not used in this implementation
//    null
//  }
//
//}