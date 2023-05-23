package com.hive.ycbm.controllers;

import com.hive.ycbm.services.MailService;
import com.hive.ycbm.dto.BookerDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final MailService mailService;

    @GetMapping("/confirm")
    public String showPage(Model model) {
        model.addAttribute("booker", new BookerDto());
        return "confirm-booking";
    }

    @PostMapping("/confirm")
    public String triggerMail(@ModelAttribute("booker") BookerDto bookerDto,
                              Model model)
            throws MessagingException {
        UserDto userDto = new UserDto(null, "Banana", "123", "thichthinhich83@gmail.com", null, null, "1");
        EventDto eventDto = new EventDto(10101L, Date.valueOf(LocalDate.now()), Duration.ofHours(1));
        model.addAttribute("booker", bookerDto);
        mailService.sendMail(bookerDto, userDto, eventDto);
        return "success";
    }
}
