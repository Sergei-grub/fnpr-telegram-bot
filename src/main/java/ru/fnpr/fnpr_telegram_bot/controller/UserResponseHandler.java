package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class UserResponseHandler {
    public void handleUserResponse(String userMessage, Long chatId) {
        // Логика обработки
    }

//    public void handleUserResponse(Update update) {
//        // Логика обработки
//    }
}
