package com.hive.ycbm.Repositories;

import com.hive.ycbm.model.BookingPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPageRepository extends JpaRepository<BookingPage, Long> {
}
