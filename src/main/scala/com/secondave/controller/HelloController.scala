package com.secondave.controller

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{GetMapping, RequestHeader, ResponseStatus}
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.{RequestMapping, RestController}
import com.google.api.services.calendar.{Calendar, CalendarScopes}
import com.google.auth.http.HttpCredentialsAdapter

import java.io.FileInputStream
import java.time.LocalDateTime
import java.util.Collections

@RestController
class HelloController:
  private val JSON_FACTORY = GsonFactory.getDefaultInstance

  @Value("${application.name}")
  val appName: String = null

  @RequestMapping(path = Array("/"), method = Array(GET))
  def root(): String = {
    val credentials: GoogleCredentials = GoogleCredentials
      .fromStream(new FileInputStream("C:\\Users\\jacki\\Downloads\\second-ave-dffbefd5c686.json"))
      .createDelegated("jackiegleason@gmail.com")
      .createScoped(Collections.singleton(CalendarScopes.CALENDAR_READONLY))
    credentials.refreshIfExpired()
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport
    val calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
      .setApplicationName(appName)
      .build()
    calendar.calendars().get("primary").execute().getSummary
  }