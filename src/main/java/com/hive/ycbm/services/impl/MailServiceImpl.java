package com.hive.ycbm.services.impl;

import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.services.MailService;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Override
    public void sendMail(UserDto userDto, EventDto eventDto) {
        Context context = new Context();
        context.setVariable("firstName", eventDto.getBooker().getFirstName());
        context.setVariable("lastName", eventDto.getBooker().getLastName());
        context.setVariable("email", eventDto.getBooker().getEmail());
        context.setVariable("title", eventDto.getEventTitle());
        context.setVariable("start", eventDto.getStart());
        context.setVariable("end", eventDto.getEnd());
        String mailBooker = templateEngine.process("mail-booker.html", context);
        createMail(eventDto.getBooker().getEmail(), "Your meeting with " + userDto.getFirstName(), mailBooker);
        String mailUser = templateEngine.process("mail-user.html", context);
        createMail(userDto.getMainEmail(), "New meeting scheduled with " + eventDto.getBooker().getFirstName(), mailUser);
    }

    public void createMail(String to, String subject, String html)  {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new CustomException("Error with MineMessage", HttpStatus.BAD_REQUEST);
        }
        mailSender.send(message);
    }
}
