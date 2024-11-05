package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.fnpr.fnpr_telegram_bot.model.Question;
import ru.fnpr.fnpr_telegram_bot.service.QuestionService;

import java.util.List;

@Service
public class BotService {
    @Autowired
    private QuestionService questionService;

    public void processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();

            // Логика обработки вопросов
            List<Question> questions = questionService.getAllQuestions();
            // Формирование ответа
            String response = generateResponse(questions);

            // Отправка сообщения
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(response);

            // Метод для отправки сообщения через бот (пока можно оставить заглушку)
            sendMessageToUser(message);
        }
    }

    private String generateResponse(List<Question> questions) {
        // Пример простой логики формирования ответа
        StringBuilder response = new StringBuilder("Выберите вопрос:\n");
        for (Question question : questions) {
            response.append(question.getId()).append(". ").append(question.getText()).append("\n");
        }
        return response.toString();
    }

    private void sendMessageToUser(SendMessage message) {
        // Здесь позже можно настроить отправку через Telegram API
    }
}
