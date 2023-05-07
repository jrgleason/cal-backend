package com.secondave.model.calendar

import com.google.api.services.calendar.model.{Event, Events}

import java.util.Date

case class EventDay(
                   day: Date,
                   events: Events
                   )
