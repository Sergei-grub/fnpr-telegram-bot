package ru.fnpr.fnpr_telegram_bot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ButtonsRepository extends JpaRepository<Buttons, Long> {
    List<Buttons> findByName(String name);
    List<Buttons> findByEmoji(String emoji);
    List<Buttons> findByCallbackData(String callbackData);
    List<Buttons> findByUrl(String url);
    List<Buttons> findByImageUrl(String imageUrl);
    List<Buttons> findByFileUrl(String fileUrl);
    List<Buttons> findByTextInfo(String textInfo);
    List<Buttons> findByType(Buttons.ButtonType type);
    List<Buttons> findByParentId(Long parentId);
    List<Buttons> findByCreatedAt(LocalDateTime createdAt);
    List<Buttons> findByStatistic(Integer statistic);
}