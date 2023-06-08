package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.User;

import java.util.List;

public interface BookingPageService {
    List<BookingPageDto> getBookingPagesByUser(String email);

    BookingPageDto findById(Long pageId);

    void saveBookingPage(BookingPage bookingPage, String email);


    void updateBookingPage(BookingPageDto bookingPageDto);

    void deleteBookingPage(Long pageId);
}
