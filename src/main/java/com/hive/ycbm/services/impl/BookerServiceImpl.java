package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.CalendarDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.*;
import com.hive.ycbm.repositories.BookerRepository;
import com.hive.ycbm.repositories.CalendarRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.repositories.UserRepository;
import com.hive.ycbm.services.BookerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BookerServiceImpl implements BookerService {
    @Autowired
    private BookerRepository bookerRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CalendarRepository calendarRepository;

    @Override
    public void createBooker(EventDto eventDto, BookingPage bookingPage) {
        Event event = new Event();
        event.setEventTitle(eventDto.getEventTitle());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());
        event.setCalendar(calendarRepository.findById(bookingPage.getCalendar().getCalendarId()).orElse(null));
        Booker booker = new Booker();
        booker.setFirstName(eventDto.getBooker().getFirstName());
        booker.setLastName(eventDto.getBooker().getLastName());
        booker.setEmail(eventDto.getBooker().getEmail());
        bookerRepository.save(booker);
        event.setBooker(booker);
        eventRepository.save(event);
    }

}
