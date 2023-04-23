package com.secondave.controller

import com.google.api.services.calendar.model.Events
import com.secondave.service.GoogleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}
import org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping(path = Array("/google"))
class GoogleController {
  @Autowired
  val googleService: GoogleService = null

  @RequestMapping(path = Array("/calendar/summary"), method = Array(GET))
  def root(): String = googleService.getCalendarSummary

  @RequestMapping(path = Array("/calendar/events"), method = Array(GET))
  def getEvents: Events = googleService.getEventsForDay
}
