package com.hive.ycbm.services;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;

import java.util.List;

public interface EventService {
    List<EventDto> getEventByCalendar(Calendar calendar);
    void deleteEvent(Long eventId);
}
