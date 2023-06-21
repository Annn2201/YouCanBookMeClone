package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.models.User;
import com.hive.ycbm.repositories.BookerRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.repositories.UserRepository;
import com.hive.ycbm.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookerRepository bookerRepository;

    @Override
    public List<EventDto> getEventByUser(String email) {
        User user = userRepository.findByMainEmail(email).orElseThrow(() -> new CustomException("Event not found with email: " + email));
        List<Event> events = eventRepository.findByUser(user);
        return events.stream().map(event -> EventDto.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .start(event.getStart())
                .end(event.getEnd())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEventByCalendar(Calendar calendar) {
        List<Event> events = eventRepository.findByCalendar(calendar);
        return events.stream().map(event -> EventDto.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .start(event.getStart())
                .end(event.getEnd())
                .build()).collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException("Booking page not found with id: " + eventId));
        return EventDto.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .start(event.getStart())
                .end(event.getEnd())
                .build();
    }
    @Override
    public void deleteEvent(Long pageId) {
        Event event = eventRepository.findById(pageId)
                .orElseThrow(() -> new CustomException("Booking page not found with id: " + pageId));
        eventRepository.delete(event);
    }
}


