package com.hive.ycbm.repositories;

import com.hive.ycbm.models.Booker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookerRepository extends JpaRepository<Booker, Long> {
}
