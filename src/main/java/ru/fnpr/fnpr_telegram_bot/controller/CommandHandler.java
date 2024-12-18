package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public String handleCommand(String userMessage) {
        // Убираем "/" из начала сообщения
        String commandName = userMessage.substring(1);

        // Ищем подходящую кнопку по имени
        List<Buttons> matchingButtons = buttonsService.filterButtons(Buttons::getName, commandName);

        // Проверяем, найдена ли кнопка
        if (matchingButtons.isEmpty()) {
            return "Команда не найдена";
        }

        // Предполагаем, что мы ищем только одну кнопку
        Buttons foundButton = matchingButtons.get(0);
        String textInfo = foundButton.getTextInfo(); // Предполагаем, что метод getTextInfo() возвращает значение поля text_info
        System.out.println("Retrieved textInfo: " + textInfo);
//        System.out.println("Contains newline: " + textInfo.contains("\n"));

        // Возвращаем значение text_info
        return textInfo;
    }
}