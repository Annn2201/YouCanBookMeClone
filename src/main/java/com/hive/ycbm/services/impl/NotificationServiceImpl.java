package com.hive.ycbm.services.impl;

import com.hive.ycbm.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    @Override
    public void sendGlobalNotification() {
        String message = "Global Notification";
        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }
    @Override
    public void notifyMessage(String message) {
        sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
