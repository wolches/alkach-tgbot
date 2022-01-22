package io.github.wolches.tgbot.pesda.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PesdaService {

    private static final String DA_REGEXP = "(^(Да|да)(\\s|,|-))|((\\s|,|\\.|-)(Да|да)(!|\\?|\\.|))$";
    private static final String PESDA = "Пизда";

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<String> pesda(String text) {
        return Optional.ofNullable(doTheFuckery(text));
    }

    private String doTheFuckery(String text) {
        return (text.matches(DA_REGEXP)) ? PESDA : null;
    }
}
