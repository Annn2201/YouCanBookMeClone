package com.hive.ycbm.repositories;

import com.hive.ycbm.models.BookingPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookingPageRepository extends JpaRepository<BookingPage, Long> {
}
