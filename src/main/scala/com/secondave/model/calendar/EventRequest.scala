package com.secondave.model.calendar

case class Event(
                  start: DateTimeWithTimeZone,
                  end: DateTimeWithTimeZone,
                  summary: String,
                  location: String,
                  description: String)
