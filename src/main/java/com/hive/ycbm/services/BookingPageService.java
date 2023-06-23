package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.User;

import java.util.List;

public interface BookingPageService {
    List<BookingPageDto> getBookingPagesByUser(String email);
    BookingPage findById(Long pageId);
    BookingPageDto getBookingPageById(Long pageId);
    void saveBookingPage(BookingPage bookingPage, String email, Calendar calendar);
    void updateBookingPage(BookingPageDto bookingPageDto);
    void deleteBookingPage(Long pageId);
}
