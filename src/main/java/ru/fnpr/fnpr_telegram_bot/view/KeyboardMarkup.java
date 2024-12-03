package ru.fnpr.fnpr_telegram_bot.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.fnpr.fnpr_telegram_bot.model.Buttons;
import ru.fnpr.fnpr_telegram_bot.model.ButtonsService;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardMarkup {

    private final ButtonsService buttonsService;

    @Autowired
    public KeyboardMarkup(ButtonsService buttonsService) {
        this.buttonsService = buttonsService;
    }

    public ReplyKeyboardMarkup createReplyKeyboard(String desiredType) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        // Создаем строки кнопок
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        // Ищем подходящие кнопки по типу
        List<Buttons> matchingButtons = buttonsService.getButtonsByType(Buttons.ButtonType.keyboard);

        // Отладочные сообщения
        System.out.println("Matching buttons: " + matchingButtons.size());
        for (Buttons button : matchingButtons) {
            System.out.println("Button name: " + button.getName());
        }

        // Перебираем все кнопки и добавляем их в строки по 2 в каждой
        for (Buttons button : matchingButtons) {
            currentRow.add(new KeyboardButton(button.getName()));
            // Если в текущей строке уже 2 кнопки, добавляем строку в клавиатуру и создаем новую
            if (currentRow.size() == 2) {
                keyboard.add(currentRow);
                currentRow = new KeyboardRow(); // Создаем новую строку
            }
        }

        // Если осталась одна кнопка в текущей строке, добавляем ее
        if (!currentRow.isEmpty()) {
            keyboard.add(currentRow);
        }

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}