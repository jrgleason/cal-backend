package com.secondave.controller

import com.auth0.client.mgmt.filter.UserFilter
import com.auth0.json.mgmt.users.User
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestHeader, ResponseStatus}
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.{RequestMapping, RestController}
import com.google.api.services.calendar.{Calendar, CalendarScopes}
import com.google.auth.http.HttpCredentialsAdapter
import com.secondave.service.{GoogleService, ManagementApiService}

import java.io.FileInputStream
import java.time.LocalDateTime
import java.util.Collections

@RestController
class HelloController:
  @Autowired
  val managementApiService: ManagementApiService = null

  @RequestMapping(path = Array("/auth0"), method = Array(GET))
  def auth0() = managementApiService.getApi.users.list(new UserFilter()).execute.getBody

  @RequestMapping(path = Array("/"), method = Array(GET))
  def hello() = "Hello World!";
