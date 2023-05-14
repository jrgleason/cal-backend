package com.secondave.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestMapping}

import java.util

@Controller
@RequestMapping(path=Array("/oidc"))
class AuthController {
  @GetMapping(path=Array("/"))
  def getInfo(@AuthenticationPrincipal principal: OidcUser): util.Map[String, AnyRef] ={
    if(principal != null){
      principal.getIdToken.getClaims
    } else {
      util.Collections.emptyMap()
    }
  }
}
