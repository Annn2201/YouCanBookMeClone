package com.hive.ycbm.repositories;
import com.hive.ycbm.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
