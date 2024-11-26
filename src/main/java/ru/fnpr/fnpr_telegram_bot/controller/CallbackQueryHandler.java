package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class CallbackQueryHandler {
    public void handleCallbackQuery(Update update) {
        String userMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        // Логика обработки
    }
}
