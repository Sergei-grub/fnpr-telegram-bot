package ru.fnpr.fnpr_telegram_bot.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class BotController extends TelegramLongPollingBot {
    @Value("${telegram.bot.name}")
    private final String botName;
//    private final ButtonsService buttonsService;

    private final CommandHandler commandHandler;
    private final UserResponseHandler userResponseHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public BotController(@Value("${telegram.bot.name}") String botName,
                         @Value("${telegram.bot.token}") String botToken,
//                         ButtonsService buttonsService,
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

    private static final Logger logger = LoggerFactory.getLogger(BotController.class);


    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Received update: {}", update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            // Обработка команды /start
            if (userMessage.equals("/start")) {
                commandHandler.handleStartCommand(chatId);
            } else {
                userResponseHandler.handleUserResponse(update);
            }
        } else if (update.hasCallbackQuery()) {
            callbackQueryHandler.handleCallbackQuery(update);
        }
    }

//    private void handleCallbackQuery(Update update) {
//        String callbackData = update.getCallbackQuery().getData();
//        Long questionId = Long.valueOf(callbackData);
//
//        Question selectedQuestion = botService.getQuestionById(questionId);
//
//        // Проверяем наличие текстового ответа или URL
//        if (selectedQuestion.getAnswerText() != null && !selectedQuestion.getAnswerText().isEmpty()) {
//            sendAnswer(selectedQuestion, update);
//        } else if (selectedQuestion.getUrl() != null && !selectedQuestion.getUrl().isEmpty()) {
//            sendUrl(selectedQuestion, update);
//        } else {
//            // Получаем вопросы следующего уровня
//            List<Question> nextLevelQuestions = botService.getQuestionsByParentId(questionId);
//
//            // Фильтруем вопросы по уровню
//            nextLevelQuestions = nextLevelQuestions.stream()
//                    .filter(q -> q.getLevel() == 2) // Убедитесь, что уровень равен 2
//                    .collect(Collectors.toList());
//
//            sendNextLevelQuestions(update, nextLevelQuestions);
//        }
//    }
//
//    private void sendUrl(Question question, Update update) {
//        SendMessage message = new SendMessage();
//
//        // Извлекаем chatId из CallbackQuery
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        message.setChatId(String.valueOf(chatId));
//        message.setText("Перейдите по ссылке: " + question.getUrl());
//
//        try {
//            execute(message); // Отправляем сообщение с URL
//        } catch (Exception e) {
//            logger.error("Ошибка при отправке сообщения: ", e);
//        }
//    }
//
//
//
//    private void handleUserResponse(Update update) {
//        List<Question> questions = botService.getQuestionsByLevel(1); // Получаем вопросы первого уровня
//        sendNextLevelQuestions(update, questions);
//    }
//
//    private void sendWelcomeMessage(Long chatId) {
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId.toString());
//        message.setText("Добро пожаловать! Выберите вопрос:");
//
//        List<Question> questions = botService.getQuestionsByLevel(1); // Получаем вопросы первого уровня
//        message.setReplyMarkup(createKeyboard(questions));
//
//        try {
//            execute(message); // Отправляем сообщение
//        } catch (Exception e) {
//            logger.error("Ошибка при отправке сообщения: ", e);
//        }
//    }
//
//
//    private InlineKeyboardMarkup createKeyboard(List<Question> questions) {
//        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//
//        for (Question question : questions) {
//            InlineKeyboardButton button = new InlineKeyboardButton();
//            button.setText(question.getQuestionText());
//            button.setCallbackData(String.valueOf(question.getId()));
//
//            rows.add(List.of(button));
//        }
//
//        markup.setKeyboard(rows);
//        return markup;
//    }
//
//
//    private void sendAnswer(Question question, Update update) {
//        SendMessage message = new SendMessage();
//        message.setChatId(update.getMessage().getChatId().toString());
//        message.setText(question.getAnswerText());
//
//        try {
//            execute(message); // Отправляем ответ
//        } catch (Exception e) {
//            logger.error("Ошибка при отправке сообщения: ", e);
//        }
//    }
//
//    private void sendNextLevelQuestions(Update update, List<Question> nextLevelQuestions) {
//        SendMessage message = new SendMessage();
//
//        // Извлекаем chatId из обновления
//        Long chatId;
//        if (update.hasMessage()) {
//            chatId = update.getMessage().getChatId();
//        } else if (update.hasCallbackQuery()) {
//            chatId = update.getCallbackQuery().getMessage().getChatId();
//        } else {
//            // Обработка случая, когда chatId не может быть получен
//            throw new IllegalArgumentException("chatId cannot be determined from the update");
//        }
//
//        message.setChatId(String.valueOf(chatId)); // Устанавливаем chatId
//        message.setText("Выберите следующий вопрос:");
//        message.setReplyMarkup(createKeyboard(nextLevelQuestions));
//
//        try {
//            execute(message); // Отправляем вопросы следующего уровня
//        } catch (Exception e) {
//            logger.error("Ошибка при отправке сообщения: ", e);
//        }
//    }

}