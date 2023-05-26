package com.hive.ycbm.services;

import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(UserDto userDto, EventDto eventDto) throws MessagingException;
}
