package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.EventsDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.services.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final BookerService bookerService;
    private final MailService mailService;
    private final CalendarService calendarService;
    private final BookingPageService bookingPageService;
    @GetMapping("/public/event/{pageId}")
    public String showCreateForm(@PathVariable("pageId") Long pageId,
                                 @ModelAttribute("bookingPage") BookingPageDto bookingPageDto) {
        return "booking-page";
    }
    @RequestMapping("/public/create-event/{pageId}")
    public String createEvent(EventDto eventDto,
                              @ModelAttribute("bookingPage") BookingPageDto bookingPageDto,
                              Model model) {
        model.addAttribute("event", eventDto);
        return "confirm-booking";
    }
    @GetMapping("/api/v1/events")
    @ResponseBody
    public List<EventsDto> listAll(){
        return eventService.getAllEvent();
    }

    @GetMapping("/event-dashboard/{pageId}")
    public String viewEventDashBoard(@ModelAttribute("currentUser") UserDto userDto,
                                     @PathVariable("pageId") Long pageId,
                                     Model model) {
        BookingPage bookingPage = bookingPageService.findById(pageId);
        Calendar calendar = calendarService.findById(bookingPage.getCalendar().getCalendarId());
        List<EventDto> listEventDto = eventService.getEventByCalendar(calendar);
        model.addAttribute("listEvent", listEventDto);
        model.addAttribute("bookingPage", bookingPage);
        return "event-dashboard";
    }
    @GetMapping("/public/confirm/{pageId}")
    public String showConfirmForm(@PathVariable(value = "pageId") Long pageId,
                                  @ModelAttribute("bookingPage") BookingPageDto bookingPageDto,
                                  @ModelAttribute("event")EventDto eventDto) {
        return "confirm-booking";
    }
    @PostMapping("/public/confirm/{pageId}")
    public String confirmEvent(@PathVariable(value = "pageId") Long pageId,
                               @ModelAttribute("event") EventDto eventDto,
                               HttpServletRequest request) {
        UserDto currentUser = userService.loadCurrentUser(request);
        BookingPage bookingPage = bookingPageService.findById(pageId);
        bookerService.createBooker(eventDto, bookingPage);
        mailService.sendMail(currentUser, eventDto);
        return "redirect:/public/success/";
    }
    @GetMapping("/public/success/")
    public String showSuccess(){
        return "success";
    }
    @DeleteMapping("/event-dashboard/{pageId}")
    public ResponseEntity deleteEvent(@PathVariable(value = "pageId") Long pageId) {
        this.eventService.deleteEvent(pageId);
        return ResponseEntity.ok().build();
    }



}

