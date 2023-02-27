package com.odbpo.flutter.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

/**
 * createDate: 2022/11/16 on 17:14
 * desc:
 *
 * @author azhon
 */


public class NotificationUtil {
    /**
     * 发送一个通知
     */
    public static void showNotify(String message) {
        NotificationGroup notificationGroup = new NotificationGroup("FlutterPlugin",
                NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification(message, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }
}
