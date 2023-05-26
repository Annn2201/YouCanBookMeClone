package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.services.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final BookerService bookerService;
    private final MailService mailService;

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
    public String confirmEvent(@ModelAttribute("event") EventDto eventDto) throws MessagingException {
        bookerService.createBooker(eventDto);
        mailService.sendMail(userService.loadCurrentUser(), eventDto);
        return "success";
    }
}

