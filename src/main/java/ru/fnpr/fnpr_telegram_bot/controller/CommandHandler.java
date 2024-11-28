package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.fnpr.fnpr_telegram_bot.model.Buttons;
import ru.fnpr.fnpr_telegram_bot.model.ButtonsService;

import java.util.List;

@Component
public class CommandHandler {
    private final ButtonsService buttonsService;

    @Autowired
    public CommandHandler(ButtonsService buttonsService) {
        this.buttonsService = buttonsService;
    }

    public void handleCommand(String commandCallbackData, Long chatId) {
        // Ищем кнопку по callbackData
        List<Buttons> matchingButtons = buttonsService.getButtonsByCallbackData(commandCallbackData);

        if (!matchingButtons.isEmpty()) {
            Buttons button = matchingButtons.get(0);
            sendResponse(button.getTextInfo(), chatId); // Отправляем текст, связанный с кнопкой
        }
    }

    private void sendResponse(String responseText, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseText); // Используем текст из кнопки


    }
}