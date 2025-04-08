package com.java.util;

import com.java.model.CardStatus;
import com.java.service.CardStatusService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class InsertCardStatusService {
    private static final Logger logger = Logger.getLogger(InsertCardStatusService.class.getName());
    private final CardStatusService cardStatusService;

    public InsertCardStatusService(CardStatusService cardStatusService) {
        this.cardStatusService = cardStatusService;
    }

    public void insertCardStatuses() {
        List<String> statuses = Arrays.asList(
                "Card is not active",
                "Card is valid",
                "Card is temporarily blocked",
                "Card is lost",
                "Card is compromised",
                "Какой-то другой статус"
        );

        for (String status : statuses) {
            try {
            CardStatus cardStatus = new CardStatus();
            cardStatus.setCardStatusName(status);
            cardStatusService.create(cardStatus);
            } catch (Exception e) {
                logger.warning("Не удалось вставить статус '" + status + "': " + e.getMessage());
            }
        }

    }
}
