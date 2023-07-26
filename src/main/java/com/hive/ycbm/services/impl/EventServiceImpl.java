package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.EventsDto;
import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.repositories.BookingPageRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.EventService;
import com.hive.ycbm.specifications.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingPageRepository bookingPageRepository;

    @Override
    @Cacheable(value = "events")
    public List<EventDto> getEventByCalendar(Calendar calendar, String keyword, Integer days) {
        List<Event> events;
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = null;

        if (days != null && days > 0) {
            startTime = endTime.minus(days, ChronoUnit.DAYS);
        }

        if (startTime != null && keyword != null) {
            Specification<Event> specification = EventSpecification.withTimeFilterAndName(startTime, endTime, keyword, calendar.getCalendarId());
            events = eventRepository.findAll(specification);
        } else if (startTime != null) {
            Specification<Event> specification = EventSpecification.withTimeFilter(startTime, endTime, calendar.getCalendarId());
            events = eventRepository.findAll(specification);
        } else if (keyword != null) {
            Specification<Event> specification = EventSpecification.withName(keyword, calendar.getCalendarId());
            events = eventRepository.findAll(specification);
        } else {
            events = eventRepository.findByCalendar(calendar);
        }

        return events.stream().map(event -> EventDto.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .start(event.getStart())
                .end(event.getEnd())
                .booker(event.getBooker())
                .calendar(event.getCalendar())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "events")
    public List<EventsDto> getEventsByCalendar(Calendar calendar) {
        List<Event> events = eventRepository.findByCalendar(calendar);
        return events.stream().map(event -> EventsDto.builder().start(event.getStart())
                .end(event.getEnd())
                .title(event.getEventTitle())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long pageId) {
        Event event = eventRepository.findById(pageId)
                .orElseThrow(() -> new CustomException("Booking page not found with id: " + pageId));
        eventRepository.delete(event);
    }
}


