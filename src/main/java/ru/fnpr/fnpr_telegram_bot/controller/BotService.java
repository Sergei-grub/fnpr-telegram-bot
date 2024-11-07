package ru.fnpr.fnpr_telegram_bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fnpr.fnpr_telegram_bot.model.Question;
import ru.fnpr.fnpr_telegram_bot.model.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BotService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsByLevel(int level) {
        return questionRepository.findByLevel(level);
    }

    public Question getQuestionById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }

    public List<Question> getQuestionsByParentId(Long parentQuestionId) {
        return questionRepository.findByParentQuestionId(parentQuestionId);
    }
}
