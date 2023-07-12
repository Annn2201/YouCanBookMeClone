package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.CalendarDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;

public interface BookerService {
    EventDto createBooker(EventDto eventDto, BookingPage bookingPage);
}
