package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.services.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final BookerService bookerService;
    private final MailService mailService;

    @GetMapping("/event")
    public String showCreateForm() {
        return "booking-page";
    }
    @PostMapping("/create-event")

    public String createEvent(EventDto eventDto,
                              Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("event", eventDto);
        return "redirect:/confirm";
    }

    @GetMapping("/confirm")
    public String showConfirmForm(@ModelAttribute("event") EventDto eventDto,
                                  Model model) {
        model.addAttribute("event", eventDto);
        return "confirm-booking";
    }

    @PostMapping("/confirm")
    public String confirmEvent(@ModelAttribute("event") EventDto eventDto,
                               RedirectAttributes redirectAttributes) throws MessagingException {
        bookerService.createBooker(eventDto);
        mailService.sendMail(userService.loadCurrentUser(), eventDto);
        redirectAttributes.addFlashAttribute("event", eventDto);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccess(@ModelAttribute("event") EventDto eventDto){
        return "success";
    }
}

