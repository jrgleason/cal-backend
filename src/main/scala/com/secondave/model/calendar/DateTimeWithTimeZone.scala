package com.secondave.model.calendar

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.EventDateTime
import org.springframework.boot.jackson.JsonComponent

case class DateTimeWithTimeZone(dateTime: String, timeZone: String){
  def getDateTime(): EventDateTime = new EventDateTime()
      .setDateTime(
        DateTime.parseRfc3339(
          dateTime.substring(0, 19)
        )
      )
      .setTimeZone(timeZone)
}