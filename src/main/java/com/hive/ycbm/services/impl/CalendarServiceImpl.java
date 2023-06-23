package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.CalendarDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.repositories.CalendarRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {
    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;

    @Override
    public void save(Calendar calendar) {
        calendarRepository.save(calendar);
    }

    @Override
    public Calendar findById(Long id) {
        Calendar calendar = calendarRepository.findById(id).orElseThrow(()
                -> new CustomException("Calendar not found"));
        return calendar;
    }
}
