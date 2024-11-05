package repository;

import model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Здесь можно добавить методы для работы с вопросами
}
