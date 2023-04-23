package com.secondave.service

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Events
import com.google.api.services.calendar.{Calendar, CalendarScopes}
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

import java.io.FileInputStream
import java.time.{LocalDate, ZoneId}
import java.util.Collections
import java.util.Date
import scala.annotation.meta.beanSetter
import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._

@Service
class GoogleService @Autowired()(
                                  @(Value @beanSetter)("${google.key.location}") @BeanProperty val keyLocation: String,
                                  @(Value @beanSetter)("${google.application.name}") @BeanProperty val appName: String,
                                  @(Value @beanSetter)("${google.calendar.id}") @BeanProperty val calendarId: String
                                ) {
  private val JSON_FACTORY = GsonFactory.getDefaultInstance
  private val credentials: GoogleCredentials = GoogleCredentials
    .fromStream(new FileInputStream(keyLocation))
    .createScoped(
      Collections.singleton(CalendarScopes.CALENDAR_EVENTS)
        .asScala
        .toSet
        .+(CalendarScopes.CALENDAR)
        .asJavaCollection
    )
  private val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport

  def getCalendarSummary: String = {
    credentials.refreshIfExpired()
    new Calendar.Builder(
      HTTP_TRANSPORT,
      JSON_FACTORY,
      new HttpCredentialsAdapter(credentials)
    )
      .setApplicationName(appName)
      .build()
      .calendars()
      .get(calendarId)
      .execute()
      .getSummary
  }
  def getEventsForDay: Events = {
    credentials.refreshIfExpired()
    val date = LocalDate.of(2023, 4, 23)
    val startOfDay = new DateTime(
      Date.from(
        date.atStartOfDay(ZoneId.systemDefault())
            .toInstant
      )
    )
    val endOfDay = new DateTime(
      Date.from(
        date.plusDays(1)
          .atStartOfDay(
            ZoneId.systemDefault()
          ).toInstant
      )
    )
    new Calendar.Builder(
      HTTP_TRANSPORT,
      JSON_FACTORY,
      new HttpCredentialsAdapter(credentials)
    )
      .setApplicationName(appName)
      .build()
      .events()
      .list(calendarId)
      .setTimeMin(startOfDay)
      .setTimeMax(endOfDay)
      .execute()
  }
}
