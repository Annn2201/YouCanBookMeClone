package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.services.BookingPageService;
import com.hive.ycbm.services.CalendarService;
import com.hive.ycbm.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookingPageController {
    @Autowired
    private BookingPageService bookingPageService;
    @Autowired
    private UserService userService;
    @Autowired
    private CalendarService calendarService;
    @GetMapping("/")
    public String viewDashBoard(@ModelAttribute("bookingPage") BookingPageDto bookingPageDto,
                                HttpServletRequest request,
                                Model model) {
        UserDto currentUser = userService.loadCurrentUser(request);
        String username = userService.loadCurrentMailEmail(request);
        List<BookingPageDto> bookingPages= bookingPageService.getBookingPagesByUser(username);
        model.addAttribute("listBookingPage", bookingPages);
        model.addAttribute("currentUser", currentUser);
        return "homepage";
    }
    @PostMapping("/booking-page")
    public String saveBookingPage(BookingPage bookingPage,
                                  HttpServletRequest request,
                                  Model model) {
        String username = userService.loadCurrentMailEmail(request);
        Calendar calendar = new Calendar();
        calendar.setCalendarEmail(username);
        calendarService.save(calendar);
        bookingPageService.saveBookingPage(bookingPage,username,calendar);
        return "redirect:/admin/";
    }
    @GetMapping("/booking-page/{pageId}")
    public String showUpdateBookingPage(@PathVariable(value = "pageId") Long pageId,
                                        @ModelAttribute("currentUser") UserDto userDto,
                                        Model model) {
        BookingPageDto bookingPageDto = bookingPageService.getBookingPageById(pageId);
        model.addAttribute("bookingPage", bookingPageDto);
        return "update_bookingPage";
    }
    @PostMapping("/booking-page/{pageId}")
    public String updateBookingPage(@ModelAttribute("bookingPage") BookingPageDto bookingPageDto
                                    ) {
        bookingPageService.updateBookingPage(bookingPageDto);
        return "redirect:/admin/booking-page/{pageId}?success";
    }
    @DeleteMapping("/booking-page/{pageId}")
    public ResponseEntity deleteBookingPage(@PathVariable(value = "pageId") Long pageId) {
        this.bookingPageService.deleteBookingPage(pageId);
        return ResponseEntity.ok().build();
    }
}
