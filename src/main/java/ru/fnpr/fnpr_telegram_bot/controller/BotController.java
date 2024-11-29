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

import java.util.regex.Pattern;
import java.util.List;


@Component
public class BotController extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private String botName;
//    private final ButtonsService buttonsService;

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
//        this.buttonsService = buttonsService;
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

            String testMsg = "Привет!\nКак дела?";
            sendResponse(chatId, testMsg);


            if (userMessage.startsWith("/")) {
                String matchingCommand = commandHandler.handleCommand(userMessage);
                sendResponse(chatId, matchingCommand);
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
        System.out.println("TEST: "+ message);

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            logger.error("Ошибка при отправке сообщения: ", e);
        }
    }
}

//        if (containsHtml(text)) {
//            message.setParseMode("HTML"); // Указываем режим парсинга как HTML
//            logger.info("Sending HTML message to chatId {}: {}", chatId, text);
//            try {
//                execute(message); // Отправляем сообщение
//            } catch (TelegramApiException e) {
//                logger.error("Ошибка при отправке сообщения: ", e);
//            }
//        } else {
//            try {
//                execute(message); // Отправляем сообщение
//            } catch (TelegramApiException e) {
//                logger.error("Ошибка при отправке сообщения: ", e);
//            }
//        }
//
//    }
//
//    private boolean containsHtml(String message) {
//        return HTML_PATTERN.matcher(message).find();
//    }