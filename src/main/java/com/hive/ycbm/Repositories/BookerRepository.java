package com.hive.ycbm.Repositories;

import com.hive.ycbm.model.Booker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookerRepository extends JpaRepository<Booker, Long> {

}
