package ru.fnpr.fnpr_telegram_bot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.fnpr.fnpr_telegram_bot.model.Buttons;
import ru.fnpr.fnpr_telegram_bot.model.ButtonsService;

import java.util.List;


@Component
public class BotController extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private String botName;
    private final ButtonsService buttonsService;

    private final CommandHandler commandHandler;
    private final UserResponseHandler userResponseHandler;
    private final CallbackQueryHandler callbackQueryHandler;
    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    @Autowired
    public BotController(@Value("${telegram.bot.name}") String botName,
                         @Value("${telegram.bot.token}") String botToken,
                         ButtonsService buttonsService,
                         CommandHandler commandHandler,
                         UserResponseHandler userResponseHandler,
                         CallbackQueryHandler callbackQueryHandler) {
        super(new DefaultBotOptions(), botToken);
        this.botName = botName;
        this.buttonsService = buttonsService;
        this.commandHandler = commandHandler;
        this.userResponseHandler = userResponseHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }


    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Received update: {}", update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            logger.info("Received message: {}", update.getMessage().getText());
            String userMessage = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            sendResponse(chatId, "Вы написали: " + userMessage);
            // Извлекаем все кнопки из кэша
            List<Buttons> commandButtons = buttonsService.getButtonsByType(Buttons.ButtonType.commands);
            // Ищем подходящую кнопку по имени
            List<Buttons> matchingButtons = buttonsService.filterButtons(Buttons::getName, userMessage.substring(1)); // Убираем "/"


            if (!matchingButtons.isEmpty()) {
                Buttons button = matchingButtons.get(0); // Если найдены совпадения, обрабатываем первую найденную кнопку
                commandHandler.handleCommand(button.getCallbackData(), chatId);

            } else {
                logger.warn("Unknown command received: {}", userMessage);
                userResponseHandler.handleUserResponse(userMessage, chatId); // Если команда не распознана, можно обработать как пользовательский ответ
            }

        } else if (update.hasCallbackQuery()) {
            callbackQueryHandler.handleCallbackQuery(update);
        }

    }
    public void sendResponse(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        logger.info("Sending message to chatId {}: {}", chatId, text);
        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            logger.error("Ошибка при отправке сообщения: ", e);
        }
    }

}