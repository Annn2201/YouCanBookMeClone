package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.*;
import com.hive.ycbm.services.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class NotifyController {
    private final NotificationService notificationService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message getMessage(String message) {
        notificationService.sendGlobalNotification();
        return new Message(message, "all", "notify");
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody String message) {
        notificationService.notifyMessage(message);
    }

    @GetMapping("/error")
    public String showError(@ModelAttribute("status") int status) {
        return "error";
    }
}
