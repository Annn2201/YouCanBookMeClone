package com.hive.ycbm.specifications;

import com.hive.ycbm.models.BookingPage;
import org.springframework.data.jpa.domain.Specification;

public class BookingPageSpecification {
    public static Specification<BookingPage> withNameAndLike(String keyword, Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("user").get("userId"), userId),
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("bookingLink"), "%" + keyword + "%")
                )
        );
    }
}
