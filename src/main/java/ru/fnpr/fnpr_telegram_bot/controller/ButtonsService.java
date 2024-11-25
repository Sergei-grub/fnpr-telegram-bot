package ru.fnpr.fnpr_telegram_bot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fnpr.fnpr_telegram_bot.model.Buttons;
import ru.fnpr.fnpr_telegram_bot.model.ButtonsRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ButtonsService {

    private final ButtonsRepository buttonsRepository;
    private List<Buttons> cachedButtons;

    @Autowired
    public ButtonsService(ButtonsRepository buttonsRepository) {
        this.buttonsRepository = buttonsRepository;
    }

    @PostConstruct
    public void loadButtonsIntoCache() {
        cachedButtons = buttonsRepository.findAll();
    }

    public List<Buttons> getAllButtons() {
        return cachedButtons;
    }

    public Optional<Buttons> getButtonById(Long id) {
        return cachedButtons.stream()
                .filter(button -> button.getId().equals(id))
                .findFirst();
    }

    public List<Buttons> filterButtons(Function<Buttons, ?> getter, Object value) {
        return cachedButtons.stream()
                .filter(button -> value.equals(getter.apply(button)))
                .collect(Collectors.toList());
    }

    public List<Buttons> getButtonsByName(String name) {
        return filterButtons(Buttons::getName, name);
    }

    public List<Buttons> getButtonsByEmoji(String emoji) {
        return filterButtons(Buttons::getEmoji, emoji);
    }

    public List<Buttons> getButtonsByCallbackData(String callbackData) {
        return filterButtons(Buttons::getCallbackData, callbackData);
    }

    public List<Buttons> getButtonsByUrl(String url) {
        return filterButtons(Buttons::getUrl, url);
    }

    public List<Buttons> getButtonsByImageUrl(String imageUrl) {
        return filterButtons(Buttons::getImageUrl, imageUrl);
    }

    public List<Buttons> getButtonsByFileUrl(String fileUrl) {
        return filterButtons(Buttons::getFileUrl, fileUrl);
    }

    public List<Buttons> getButtonsByTextInfo(String textInfo) {
        return filterButtons(Buttons::getTextInfo, textInfo);
    }

    public List<Buttons> getButtonsByType(Buttons.ButtonType type) {
        return filterButtons(Buttons::getType, type);
    }

    public List<Buttons> getButtonsByParentId(Long parentId) {
        return filterButtons(Buttons::getParentId, parentId);
    }

    public List<Buttons> getButtonsByCreatedAt(LocalDateTime createdAt) {
        return filterButtons(Buttons::getCreatedAt, createdAt);
    }

    public List<Buttons> getButtonsByStatistic(Integer statistic) {
        return filterButtons(Buttons::getStatistic, statistic);
    }
}
