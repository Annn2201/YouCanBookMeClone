package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.User;
import com.hive.ycbm.services.BookingPageService;
import com.hive.ycbm.services.CalendarService;
import com.hive.ycbm.services.UserService;
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
    public String viewDashBoard(@ModelAttribute("currentUser")UserDto userDto,
                                @ModelAttribute("bookingPage")BookingPageDto bookingPageDto,
                                Model model, Long pageId) {
        String email = userService.loadCurrentMailEmail();
        List<BookingPageDto> bookingPage = bookingPageService.getBookingPagesByUser(email);
        model.addAttribute("listBookingPage", bookingPage);
        return "homepage";
    }
    @PostMapping("/booking-page")
    public String saveBookingPage(BookingPage bookingPage,
                                  Model model) {
        String emailUser = userService.loadCurrentMailEmail();
        Calendar calendar = new Calendar();
        calendar.setCalendarEmail(emailUser);
        calendarService.save(calendar);
        bookingPageService.saveBookingPage(bookingPage,emailUser,calendar);
        model.addAttribute("bookingPage", bookingPage);
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
