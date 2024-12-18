package ru.fnpr.fnpr_telegram_bot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.fnpr.fnpr_telegram_bot.model.Buttons;
import ru.fnpr.fnpr_telegram_bot.model.ButtonsService;
import ru.fnpr.fnpr_telegram_bot.view.InlineKeyboard;
import ru.fnpr.fnpr_telegram_bot.view.KeyboardMarkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class BotController extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private String botName;
//    private final ButtonsService buttonsService;

    private final BotCommands botCommands;
    private final CommandHandler commandHandler;
    private final UserResponseHandler userResponseHandler;
    private final CallbackQueryHandler callbackQueryHandler;
    private static final Logger logger = LoggerFactory.getLogger(BotController.class);
    private final KeyboardMarkup keyboardMarkup;
    private final InlineKeyboard inlineKeyboard;

    @Autowired
    public BotController(@Value("${telegram.bot.name}") String botName,
                         @Value("${telegram.bot.token}") String botToken,
                         ButtonsService buttonsService,
                         BotCommands botCommands,
                         CommandHandler commandHandler,
                         UserResponseHandler userResponseHandler,
                         CallbackQueryHandler callbackQueryHandler,
                         KeyboardMarkup keyboardMarkup,
                         InlineKeyboard inlineKeyboard) {
        super(new DefaultBotOptions(), botToken);
        this.botName = botName;
        this.botCommands = botCommands;
//        this.buttonsService = buttonsService;
        this.commandHandler = commandHandler;
        this.userResponseHandler = userResponseHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.keyboardMarkup = keyboardMarkup;
        this.inlineKeyboard = inlineKeyboard;

// Устанавливаем команды при инициализации бота
        setBotCommands();
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


            // Создаем обычные кнопки
            ReplyKeyboardMarkup keyboard = keyboardMarkup.createReplyKeyboard("keyboard");
            sendResponse(chatId, testMsg, keyboard);


            if (userMessage.startsWith("/")) {
                String matchingCommand = commandHandler.handleCommand(userMessage);
                sendResponse(chatId, matchingCommand, keyboard);
            } else {
                logger.warn("Unknown command received: {}", userMessage);
                userResponseHandler.handleUserResponse(userMessage, chatId); // Если команда не распознана, можно обработать как пользовательский ответ
            }

        } else if (update.hasCallbackQuery()) {
            // Обработка нажатия кнопки
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            // Пример создания Web App кнопки
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboard.createWebAppButton("Получить юридическую консультацию", "https://fnpr.ru/legal/");

            // Отправляем сообщение с инлайн-кнопкой
            sendResponseWithInlineKeyboard(chatId, "Нажмите на кнопку ниже, чтобы открыть веб-приложение:", inlineKeyboardMarkup);
        }
    }

    // Метод для установки команд
    private void setBotCommands() {
        List<Buttons> commandButtons = botCommands.getCommandButtons();
        List<BotCommand> commands = new ArrayList<>();

        for (Buttons button: commandButtons) {
            commands.add(new BotCommand(button.getName(), button.getTextInfo()));
        }

        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commands);

        try {
            execute(setMyCommands); // Устанавливаем команды
        } catch (TelegramApiException e) {
            logger.error("Ошибка при установке команд: ", e);
        }
    }

    // Обновленный метод sendResponse
    public void sendResponse(long chatId, String text, ReplyKeyboardMarkup replyKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        System.out.println("TEST: " + message);

        if (replyKeyboard != null) {
            message.setReplyMarkup(replyKeyboard); // Устанавливаем обычную клавиатуру, если она не null
        }

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            logger.error("Ошибка при отправке сообщения: ", e);
        }
    }


    public void sendResponseWithInlineKeyboard(long chatId, String text, InlineKeyboardMarkup inlineKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        if (inlineKeyboard != null) {
            message.setReplyMarkup(inlineKeyboard); // Устанавливаем инлайн клавиатуру, если она не null
        }

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            logger.error("Ошибка при отправке сообщения: ", e);
        }
    }
}