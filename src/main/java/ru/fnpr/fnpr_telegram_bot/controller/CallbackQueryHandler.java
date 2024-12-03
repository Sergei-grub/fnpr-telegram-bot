package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.fnpr.fnpr_telegram_bot.view.ButtonView;

import java.util.AbstractMap;

import java.util.AbstractMap;
@Component

public class CallbackQueryHandler {

    public AbstractMap.SimpleEntry<String, InlineKeyboardMarkup> handleCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        String responseText = "";
        InlineKeyboardMarkup inlineKeyboard = null;

        if ("button_click".equals(callbackData)) {
            // Обработка нажатия на кнопку
            responseText = "Вы нажали на кнопку!";
            // Создание новой инлайн клавиатуры, если нужно
            ButtonView buttonView = new ButtonView();
            inlineKeyboard = buttonView.createInlineKeyboard(); // Создайте новую клавиатуру, если необходимо
        }

        return new AbstractMap.SimpleEntry<>(responseText, inlineKeyboard);
    }
}