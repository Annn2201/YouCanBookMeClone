package com.hive.ycbm.repositories;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingPageRepository extends JpaRepository<BookingPage, Long>, JpaSpecificationExecutor<BookingPage> {
    List<BookingPage> findByUser(User user);
    List<BookingPage> findAll(Specification<BookingPage> specification);
}
