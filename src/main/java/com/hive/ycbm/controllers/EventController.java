package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.Event;
import com.hive.ycbm.repositories.BookingPageRepository;
import com.hive.ycbm.repositories.CalendarRepository;
import com.hive.ycbm.repositories.EventRepository;
import com.hive.ycbm.services.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/event/{pageId}")
    public String showCreateForm(@PathVariable("pageId") Long pageId,
                                 @ModelAttribute("bookingPage") BookingPageDto bookingPageDto) {
        return "booking-page";
    }
    @RequestMapping("/create-event/{pageId}")
    public String createEvent(EventDto eventDto,
                              @ModelAttribute("bookingPage") BookingPageDto bookingPageDto,
                              Model model) {
        model.addAttribute("event", eventDto);
        return "confirm-booking";
    }
    @GetMapping("/event-dashboard/{pageId}")
    public String viewEventDashBoard(@ModelAttribute("currentUser") UserDto userDto,
                                @PathVariable("pageId") Long pageId, Event event,
                                Model model) {
        String email = userService.loadCurrentMailEmail();
        model.addAttribute("event", event);

        BookingPage bookingPage = bookingPageService.findById(pageId);
        Calendar calendar = calendarService.findByBookingPage(bookingPage);
        List<EventDto> eventDto = eventService.getEventByCalendar(calendar);
        model.addAttribute("listEvent", eventDto);
        model.addAttribute("bookingPage", bookingPage);
        return "event";
    }
    @GetMapping("/confirm/{pageId}")
    public String showConfirmForm(@PathVariable(value = "pageId") Long pageId,
                                  @ModelAttribute("bookingPage") BookingPageDto bookingPageDto,
                                  @ModelAttribute("event")EventDto eventDto) {
        return "confirm-booking";
    }
    @PostMapping("/confirm/{pageId}")
    public String confirmEvent(@PathVariable(value = "pageId") Long pageId,
                                   @ModelAttribute("event") EventDto eventDto,
                               RedirectAttributes redirectAttributes) throws MessagingException {
        BookingPage bookingPage = bookingPageService.findById(pageId);
        bookerService.createBooker(eventDto, bookingPage);
        mailService.sendMail(userService.loadCurrentUser(), eventDto);
        redirectAttributes.addFlashAttribute("event", eventDto);
        return "redirect:/success";
    }
    @GetMapping("/success")
    public String showSuccess(@ModelAttribute("event") EventDto eventDto){
        return "success";
    }
    @DeleteMapping("/event-dashboard/{pageId}")
    public ResponseEntity deleteEvent(@PathVariable(value = "pageId") Long pageId) {
        this.eventService.deleteEvent(pageId);
        return ResponseEntity.ok().build();
    }
}

