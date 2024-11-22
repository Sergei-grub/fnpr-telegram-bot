package ru.fnpr.fnpr_telegram_bot.model;

import org.springframework.stereotype.Repository;
import ru.fnpr.fnpr_telegram_bot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
    List<Question> findByLevel(int level);
    List<Question> findByParentQuestionId(Long parentQuestionId);

}