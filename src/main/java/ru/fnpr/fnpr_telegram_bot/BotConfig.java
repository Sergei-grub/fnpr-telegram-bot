package ru.fnpr.fnpr_telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.fnpr.fnpr_telegram_bot.controller.BotController;

@Configuration
public class BotConfig {

    @Value("${telegram.bot.name}")
    private String botName;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        // Используем конструктор с DefaultBotSession
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        BotController botController = new BotController(botName, botToken); // Передаем параметры в контроллер
        botsApi.registerBot(botController); // Регистрируем бота
        return botsApi;
    }
}