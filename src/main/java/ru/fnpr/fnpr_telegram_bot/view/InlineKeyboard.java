package ru.fnpr.fnpr_telegram_bot.view;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;
@Component
public class InlineKeyboard {

    // Метод для создания инлайн-кнопки с URL веб-приложения
    public InlineKeyboardMarkup createWebAppButton(String buttonText, String webAppUrl) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setWebApp(new WebAppInfo(webAppUrl)); // Устанавливаем web_app для кнопки

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);

        return markup;
    }

    // Метод для создания обычной инлайн-кнопки
    public InlineKeyboardMarkup createButton(String buttonText, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setCallbackData(callbackData); // Устанавливаем callback_data для кнопки

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);

        return markup;
    }
}
