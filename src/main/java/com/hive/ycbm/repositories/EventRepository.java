package com.hive.ycbm.repositories;

import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCalendar(Calendar calendar);
}
