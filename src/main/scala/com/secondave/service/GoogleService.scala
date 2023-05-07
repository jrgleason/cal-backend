package com.secondave.service

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.{Calendar, CalendarScopes}
import com.google.api.services.calendar.model.{Event, Events}
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service
import com.secondave.model.calendar.{EventDay, Event as IEvent}

import java.io.FileInputStream
import java.time.{LocalDate, ZoneId}
import java.util.Collections
import java.util.Date
import java.util.Calendar as JCalendar
import scala.annotation.meta.beanSetter
import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

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

  def addEvent(event:Event) = {
    credentials.refreshIfExpired()
    new Calendar.Builder(
      HTTP_TRANSPORT,
      JSON_FACTORY,
      new HttpCredentialsAdapter(credentials)
    )
      .setApplicationName(appName)
      .build()
      .events()
      .insert(calendarId, event)
      .execute()
  }

  def getEventsForDay(day: Date): Events = {
    credentials.refreshIfExpired()
    val startOfDay = new DateTime(
      day
    )
    val c: JCalendar = JCalendar.getInstance()
    c.setTime(day)
    c.add(JCalendar.DATE, 2)
    val endOfDay = new DateTime(c.getTime)
    System.out.println("Getting from "+startOfDay.toStringRfc3339+" To "+endOfDay.toStringRfc3339+" For "+calendarId)
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

  def getEventDay(day: Date): EventDay =
    EventDay(day, getEventsForDay(day))
}
object GoogleService {
  def convertEvent(event: IEvent): Event = new Event()
      .setSummary(event.summary)
      .setDescription(event.description)
      .setLocation(event.location)
      .setStart(event.start.getDateTime())
      .setEnd(event.end.getDateTime())
}