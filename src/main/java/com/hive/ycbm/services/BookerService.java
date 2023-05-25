package com.hive.ycbm.services;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.Booker;

public interface BookerService {
    void createBooker(EventDto eventDto);
}
