package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.stereotype.Component;

@Component
public class CommandHandler {
    public void handleCommand(String command, Long chatId) {
        switch (command) {
            case "/start":
                handleStartCommand(chatId);
                break;
            case "/about":
                handleAboutCommand(chatId);
                break;
            case "/info":
                handleInfoCommand(chatId);
                break;
            // Добавьте другие команды по мере необходимости
            default:
                // Обработка неизвестной команды
                break;
        }
    }

    public void handleStartCommand(Long chatId) {
        // Ваша логика для обработки команды /start
    }

    public void handleAboutCommand(Long chatId) {
        // Ваша логика для обработки команды /about
    }

    public void handleInfoCommand(Long chatId) {
        // Ваша логика для обработки команды /info
    }
}
