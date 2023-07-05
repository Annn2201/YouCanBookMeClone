package com.hive.ycbm.services;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.EventsDto;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;

import java.util.List;

public interface EventService {
    List<EventDto> getEventByCalendar(Calendar calendar);
    List<EventsDto> getEventsByCalendar(Calendar calendar);
    void deleteEvent(Long eventId);
    List<EventsDto> getAllEvent();
}
