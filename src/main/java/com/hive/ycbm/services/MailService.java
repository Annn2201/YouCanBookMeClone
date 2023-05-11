package com.hive.ycbm.services;

import com.hive.ycbm.dto.BookerDto;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(BookerDto bookerDto, UserDto userDto, EventDto eventDto) throws MessagingException;
}
