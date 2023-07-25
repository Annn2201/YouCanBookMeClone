package com.hive.ycbm.repositories;

import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByCalendar(Calendar calendar);
    List<Event> findAll(Specification<Event> specification);
}
