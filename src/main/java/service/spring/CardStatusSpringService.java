package service.spring;

import dao.DaoInterfaceSpring;

import model.CardStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardStatusSpringService {
    private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;

    public CardStatusSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
        this.cardStatusDao = cardStatusDao;
    }


    public Optional<CardStatus> save(String cardStatusName) {
        if (cardStatusDao.findByValue(cardStatusName).isPresent()) {
            return Optional.empty();
        }
        CardStatus cardStatus = new CardStatus();
        cardStatus.setCardStatusName(cardStatusName);
        CardStatus savedCardStatus = cardStatusDao.insert(cardStatus);
        return Optional.ofNullable(savedCardStatus);
    }


    public Optional<CardStatus> getById(Long id) {
        return cardStatusDao.findById(id);
    }


    public List<CardStatus> getAll() {
        return cardStatusDao.findAll();
    }


    public Optional<CardStatus> update(CardStatus entity) {
        Optional<CardStatus> existingCardStatus = cardStatusDao.findById(entity.getId());
        if (existingCardStatus.isEmpty()) {
            return Optional.empty();
        }
        CardStatus cardStatus = cardStatusDao.update(entity);
        return Optional.ofNullable(cardStatus);
    }


    public boolean delete(Long id) {
        return cardStatusDao.delete(id);
    }


    public boolean deleteALL() {
        return cardStatusDao.deleteAll();
    }


    public Optional<CardStatus> getByName(String name) {
        return cardStatusDao.findByValue(name);
    }


    public boolean dropTable() {
        return cardStatusDao.dropTable();
    }
}
