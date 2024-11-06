package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.fnpr.fnpr_telegram_bot.service.BotService;
import ru.fnpr.fnpr_telegram_bot.model.Question;

import java.util.ArrayList;
import java.util.List;

public class BotController extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;

    @Autowired
    private BotService botService;

    public BotController(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (userMessage.equals("/start")) {
                sendWelcomeMessage(chatId);
            } else {
                handleUserResponse(update);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleUserResponse(Update update) {
        List<Question> questions = botService.getQuestionsByLevel(1); // Получаем вопросы первого уровня
        sendNextLevelQuestions(update, questions);
    }

    private void sendWelcomeMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Добро пожаловать! Выберите вопрос:");

        List<Question> questions = botService.getQuestionsByLevel(1); // Получаем вопросы первого уровня
        message.setReplyMarkup(createKeyboard(questions));

        try {
            execute(message); // Отправляем сообщение
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup createKeyboard(List<Question> questions) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Question question : questions) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(question.getQuestionText());
            button.setCallbackData(String.valueOf(question.getId()));

            rows.add(List.of(button));
        }

        markup.setKeyboard(rows);
        return markup;
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long questionId = Long.valueOf(callbackData);

        Question selectedQuestion = botService.getQuestionById(questionId);

        if (selectedQuestion.getAnswerText() != null) {
            sendAnswer(selectedQuestion, update);
        } else {
            List<Question> nextLevelQuestions = botService.getQuestionsByParentId(questionId);
            sendNextLevelQuestions(update, nextLevelQuestions);
        }
    }

    private void sendAnswer(Question question, Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(question.getAnswerText());

        try {
            execute(message); // Отправляем ответ
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNextLevelQuestions(Update update, List<Question> nextLevelQuestions) {
        SendMessage message = new SendMessage();
        message.setText("Выберите следующий вопрос:");
        message.setReplyMarkup(createKeyboard(nextLevelQuestions));

        try {
            execute(message); // Отправляем вопросы следующего уровня
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
