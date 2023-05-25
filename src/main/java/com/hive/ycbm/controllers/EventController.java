package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookerDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.models.Booker;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.repositories.BookerRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.BookerService;
import com.hive.ycbm.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Controller
    public class EventController {
        @Autowired
        private EventService eventService;
        @Autowired
        private BookerService bookerService;

    @GetMapping("/booking-page")
    public String showCreateForm() {
        return "booking-page";
    }
    @PostMapping("/create-event")
    public String createEvent(EventDto eventDto,
                              Model model) {
        model.addAttribute("event", eventDto);
        return "confirm-booking";
    }
    @GetMapping("/confirm")
    public String showConfirmForm(@ModelAttribute("event") EventDto eventDto,
                                  Model model) {
        model.addAttribute("event", eventDto);
        return "confirm-booking";
    }
    @PostMapping("/confirm")
    public String confirmEvent(@ModelAttribute("event") EventDto eventDto) {
        bookerService.createBooker(eventDto);
        return "redirect:/confirm?success";
    }
}

