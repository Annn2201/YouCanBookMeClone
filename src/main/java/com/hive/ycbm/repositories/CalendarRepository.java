package com.hive.ycbm.repositories;
import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.CalendarDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByBookingPage(BookingPage bookingPage);
}
