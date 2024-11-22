package ru.fnpr.fnpr_telegram_bot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "buttons")  // Указываем имя таблицы в базе данных
@Getter
@Setter
public class Buttons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "emoji")
    private String emoji;

    @Column(name = "callback_data")
    private String callbackData;

    @Column(name = "url")
    private String url;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "text_info", columnDefinition = "TEXT")
    private String textInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ButtonType type;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "created_at", updatable = false)  // Поле для хранения времени создания записи
    private LocalDateTime createdAt;

    @Column(name = "statistic", columnDefinition = "INT DEFAULT 0")  // Поле для подсчета обращений
    private Integer statistic;

    // Enum для типа кнопки
    public enum ButtonType {
        keyboard,
        inline,
        web_view,
        text
    }
}