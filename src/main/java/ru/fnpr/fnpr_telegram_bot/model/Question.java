package ru.fnpr.fnpr_telegram_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;  // добавлено
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "questions")  // Указываем имя таблицы в базе данных
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "parent_question_id")
    private Long parentQuestionId; // Название столбца должно быть точно таким

    @Column(name = "level")
    private int level;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "url")
    private String url;
}
