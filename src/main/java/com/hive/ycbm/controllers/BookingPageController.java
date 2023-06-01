package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.services.BookingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/admin")
public class BookingPageController {
    @Autowired
    private BookingPageService bookingPageService;
    @GetMapping("/")
    public String viewDashBoard(@ModelAttribute("currentUser")UserDto userDto,
                                Model model ) {
        BookingPage bookingPage = new BookingPage();
        model.addAttribute("bookingPage", bookingPage);
        model.addAttribute("listBookingPage",bookingPageService.getAllBookingPage());
        return "homepage";
    }

    @PostMapping("/saveBookingPage")
    public String saveBookingPage(@ModelAttribute("bookingPage") BookingPage bookingPage) {
        bookingPageService.saveBookingPage(bookingPage);
        return "redirect:/api/v1/admin/";
    }
    @GetMapping("/updateBookingPage/{pageId}")
    public String showUpdateBookingPage(@PathVariable(value = "pageId") Long pageId,
                                        @ModelAttribute("currentUser") UserDto userDto,
                                        Model model) {
        BookingPageDto bookingPageDto = bookingPageService.findById(pageId);
        model.addAttribute("bookingPage", bookingPageDto);
        return "update_bookingPage";
    }
    @PostMapping("/updateBookingPage/{pageId}")
    public String updateBookingPage(@ModelAttribute("bookingPage") BookingPageDto bookingPageDto
                                    ) {
        bookingPageService.updateBookingPage(bookingPageDto);
        return "redirect:/api/v1/admin/updateBookingPage/{pageId}?success";
    }

    @DeleteMapping("/bookingPage/{pageId}")
    public ResponseEntity deleteBookingPage(@PathVariable(value = "pageId") Long pageId) {
        this.bookingPageService.deleteBookingPage(pageId);
        return ResponseEntity.ok().build();
    }


}
