package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.models.BookingPage;

import java.util.List;

public interface BookingPageService {
    List<BookingPageDto> getAllBookingPage();

    BookingPageDto findById(Long pageId);

    void saveBookingPage(BookingPage bookingPage);

    void updateBookingPage(BookingPageDto bookingPageDto);

    void deleteBookingPage(Long pageId);
}
