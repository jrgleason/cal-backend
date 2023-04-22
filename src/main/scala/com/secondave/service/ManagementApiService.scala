package com.secondave.service

import com.auth0.client.auth.AuthAPI
import com.auth0.client.mgmt.ManagementAPI
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ManagementApiService {
  @Value("${auth0.domain}")
  val domain: String = null
  @Value("${auth0.clientId}")
  val clientId: String = null
  @Value("${auth0.clientSecret}")
  val clientSecret: String = null
  def getApi = {
    val authAPI = AuthAPI.newBuilder(
      domain,
      clientId,
      clientSecret
    ).build
    val tokenRequest = authAPI.requestToken(s"https://$domain/api/v2/")
    val holder = tokenRequest.execute.getBody
    val accessToken = holder.getAccessToken
    ManagementAPI.newBuilder(domain, accessToken).build
  }
}
