package com.secondave.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

//@Configuration
class Auth0Config(
                   @Value("${auth0.audience}") audience: String,
                   @Value("${auth0.issuer}") issuer: String,
                   @Value("${auth0.client-id}") clientId: String,
                   @Value("${auth0.client-secret}") clientSecret: String
                 ) {

  private val algorithm = Algorithm.HMAC256(clientSecret)

  def validateJwt(jwt: String): Boolean = {
    val verifier = JWT.require(algorithm)
      .withIssuer(issuer)
      .withAudience(audience)
      .build()
    verifier.verify(jwt)
    true
  }

}