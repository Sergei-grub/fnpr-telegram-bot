package ru.fnpr.fnpr_telegram_bot.view;



import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonView {

    public InlineKeyboardMarkup createStartButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton startButton = InlineKeyboardButton.builder()
                .text("Начать")
                .callbackData("start") // callback_data для обработки нажатия
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(startButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        markup.setKeyboard(keyboard);
        return markup;
    }
}