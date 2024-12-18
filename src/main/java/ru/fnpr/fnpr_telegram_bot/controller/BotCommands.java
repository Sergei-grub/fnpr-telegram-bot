package ru.fnpr.fnpr_telegram_bot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.fnpr.fnpr_telegram_bot.model.Buttons;
import ru.fnpr.fnpr_telegram_bot.model.ButtonsService;

import java.util.ArrayList;
import java.util.List;


@Component
public class BotCommands {

    private final ButtonsService buttonsService;

    @Autowired
    public BotCommands(ButtonsService buttonsService) {
        this.buttonsService = buttonsService;
    }

    public List<Buttons> getCommandButtons() {
        // Используем метод ButtonsService для получения кнопок типа COMMAND
        return buttonsService.getButtonsByType(Buttons.ButtonType.commands);
    }


}