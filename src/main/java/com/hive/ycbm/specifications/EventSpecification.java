package com.hive.ycbm.specifications;

import com.hive.ycbm.models.Event;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {
    public static Specification<Event> withName(String keyword, Long calendarId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("calendar").get("calendarId"), calendarId),
                criteriaBuilder.like(root.get("eventTitle"), "%" + keyword + "%")
        );
    }
}
