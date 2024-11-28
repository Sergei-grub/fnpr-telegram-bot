package ru.fnpr.fnpr_telegram_bot.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.slf4j.Logger;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BotController.class);
    private final BotController botController; // Ссылка на экземпляр BotController

    @Autowired
    public BotInitializer(BotController botController) {
        this.botController = botController;
    }

    @Override
    public void run(String... args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class); // Это правильный импорт
        try {
            botsApi.registerBot(botController); // Регистрация бота
            System.out.println("Bot registered successfully");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}