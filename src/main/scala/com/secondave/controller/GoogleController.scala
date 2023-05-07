package com.secondave.controller

import com.google.api.services.calendar.model.{Event, EventDateTime, Events}
import com.secondave.model.calendar.{EventDay, Event as IEvent}
import com.secondave.service.GoogleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RequestMapping, RestController}
import org.springframework.web.bind.annotation.RequestMethod.GET
import java.util.Calendar as JCalendar
import java.util.Date

@RestController
@RequestMapping(path = Array("/google"))
class GoogleController {
  @Autowired
  val googleService: GoogleService = null



  @GetMapping(path = Array("/calendar/summary"))
  def root: String = googleService.getCalendarSummary

  @PostMapping(path = Array("/calendar/events"))
  def getEvents(@RequestBody days: Array[Date]) = {
    val cal = JCalendar.getInstance()
    days.map(date => {
      googleService.getEventDay(date)
    })
  }
//    days.toList.map(googleService.getEventDay)


  // make a PostMapping to add a Event to the calendar
  @PostMapping(path = Array("/calendar/event"))
  def addEvent(@RequestBody event: IEvent): Unit =
    googleService.addEvent(GoogleService.convertEvent(event))

}