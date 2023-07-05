package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.EventsDto;
import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.repositories.BookingPageRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingPageRepository bookingPageRepository;
    @Override
    public List<EventDto> getEventByCalendar(Calendar calendar) {
        List<Event> events = eventRepository.findByCalendar(calendar);
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
    @Override
    public List<EventsDto> getAllEvent() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> EventsDto.builder()
                .title(event.getEventTitle())
                .start(event.getStart())
                .end(event.getEnd())
                .build()
        ).collect(Collectors.toList());
    }
}


