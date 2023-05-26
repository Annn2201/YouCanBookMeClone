package com.hive.ycbm.services.impl;

import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
}


