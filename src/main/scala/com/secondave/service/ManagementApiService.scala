package com.secondave.service

import com.auth0.client.auth.AuthAPI
import com.auth0.client.mgmt.ManagementAPI
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import scala.annotation.meta.beanSetter
import scala.beans.BeanProperty

@Service
class ManagementApiService(
                            @(Value @beanSetter)("${auth0.domain}") @BeanProperty var domain: String,
                            @(Value @beanSetter)("${auth0.clientId}") @BeanProperty var clientId: String,
                            @(Value @beanSetter)("${auth0.clientSecret}") @BeanProperty var clientSecret: String
                          ) {
  def getApi: ManagementAPI = {
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
