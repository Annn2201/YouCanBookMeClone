package com.hive.ycbm.specifications;

import com.hive.ycbm.models.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification {
    public static Specification<Event> withName(String keyword, Long calendarId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("calendar").get("calendarId"), calendarId),
                criteriaBuilder.like(root.get("eventTitle"), "%" + keyword + "%")
        );
    }

    public static Specification<Event> withTimeFilter(LocalDateTime startTime, LocalDateTime endTime, Long calendarId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("calendar").get("calendarId"), calendarId),
                criteriaBuilder.between(root.get("start"), startTime, endTime)
        );
    }

    public static Specification<Event> withTimeFilterAndName(LocalDateTime startTime, LocalDateTime endTime, String keyword, Long calendarId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("calendar").get("calendarId"), calendarId));
            predicates.add(criteriaBuilder.between(root.get("start"), startTime, endTime));
            predicates.add(criteriaBuilder.like(root.get("eventTitle"), "%" + keyword + "%"));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
