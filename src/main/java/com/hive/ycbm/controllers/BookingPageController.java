package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.models.User;
import com.hive.ycbm.services.BookingPageService;
import com.hive.ycbm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookingPageController {
    @Autowired
    private BookingPageService bookingPageService;
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String viewDashBoard(@ModelAttribute("currentUser")UserDto userDto,
                                Model model, Long pageId) {
        String email = userService.loadCurrentMailEmail();
        BookingPage bookingPage = new BookingPage();
        model.addAttribute("bookingPage", bookingPage);
        model.addAttribute("listBookingPage",bookingPageService.getBookingPagesByUser(email));
        return "homepage";
    }
    @PostMapping("/booking-page")
    public String saveBookingPage(@ModelAttribute("bookingPage") BookingPage bookingPage) {
        String emailUser = userService.loadCurrentMailEmail();
        bookingPageService.saveBookingPage(bookingPage,emailUser);
        return "redirect:/admin/";
    }
    @GetMapping("/booking-page/{pageId}")
    public String showUpdateBookingPage(@PathVariable(value = "pageId") Long userId,
                                        @ModelAttribute("currentUser") UserDto userDto,
                                        Model model) {
        BookingPageDto bookingPageDto = bookingPageService.findById(userId);
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
