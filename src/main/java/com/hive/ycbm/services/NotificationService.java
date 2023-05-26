package com.hive.ycbm.services;

public interface NotificationService {
    void sendGlobalNotification();
    void notifyMessage(String message);
}