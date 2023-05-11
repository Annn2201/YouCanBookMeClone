package com.hive.ycbm.services.impl;

import com.hive.ycbm.services.MailService;
import com.hive.ycbm.dto.BookerDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendMail(BookerDto bookerDto, UserDto userDto, EventDto eventDto) throws MessagingException {
        Context context = new Context();
        context.setVariable("firstName", bookerDto.getFirstName());
        context.setVariable("lastName", bookerDto.getLastName());
        context.setVariable("email", bookerDto.getEmail());
        context.setVariable("user", userDto.getFirstName());
        context.setVariable("ref", eventDto.getEventId());
        context.setVariable("date", eventDto.getStart());
        context.setVariable("duration", eventDto.getDuration());
        String mailBooker = templateEngine.process("mail-booker.html", context);
        createMail(bookerDto.getEmail(), "Your meeting with " + userDto.getFirstName(), mailBooker);
        String mailUser = templateEngine.process("mail-user.html", context);
        createMail(userDto.getMainEmail(), "New meeting scheduled with " + bookerDto.getFirstName(), mailUser);
    }

    public void createMail(String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }
}
