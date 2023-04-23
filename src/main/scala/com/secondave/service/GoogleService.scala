package com.secondave.service

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.{Calendar, CalendarScopes}
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import java.io.FileInputStream
import java.util.Collections

@Service
class GoogleService {
  private val JSON_FACTORY = GsonFactory.getDefaultInstance

  @Value("${google.key.location}")
  val keyLocation: String = null
  @Value("${google.application.name}")
  val appName: String = null

  def getCalendarSummary = {
    val credentials: GoogleCredentials = GoogleCredentials
      .fromStream(new FileInputStream(keyLocation))
      .createScoped(Collections.singleton(CalendarScopes.CALENDAR_READONLY))
    credentials.refreshIfExpired()
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport
    new Calendar.Builder(
      HTTP_TRANSPORT,
      JSON_FACTORY,
      new HttpCredentialsAdapter(credentials)
    )
      .setApplicationName(appName)
      .build()
      .calendars()
      .get("primary")
      .execute()
      .getSummary
  }
}
