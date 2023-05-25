package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.Booker;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.repositories.BookerRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.BookerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookerServiceImpl implements BookerService {
    @Autowired
    private BookerRepository bookerRepository;
    @Autowired
    private EventRepository eventRepository;
    @Override
    public void createBooker(EventDto eventDto) {
        Event event = new Event();
        event.setEventTitle(eventDto.getEventTitle());
        event.setStart(eventDto.getStart());
        event.setEnd(eventDto.getEnd());

        Booker booker = new Booker();
        booker.setFirstName(eventDto.getBooker().getFirstName());
        booker.setLastName(eventDto.getBooker().getLastName());
        booker.setEmail(eventDto.getBooker().getEmail());
        eventRepository.save(event);
        bookerRepository.save(booker);
    }
}
