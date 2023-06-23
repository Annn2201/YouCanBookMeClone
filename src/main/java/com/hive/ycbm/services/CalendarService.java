package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.CalendarDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;

import java.util.Optional;

public interface CalendarService {
    void save(Calendar calendar);
    Calendar findById(Long id);
}
