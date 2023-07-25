package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookingPageService {
    List<BookingPageDto> getBookingPagesByUser(String email, String keyword);
    BookingPage findById(Long pageId);
    BookingPageDto getBookingPageById(Long pageId);
    BookingPageDto saveBookingPage(BookingPage bookingPage, String email, Calendar calendar);
    BookingPageDto updateBookingPage(BookingPageDto bookingPageDto);
    void deleteBookingPage(Long pageId);
}
