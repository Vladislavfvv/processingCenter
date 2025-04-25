package service.spring;

import dao.DaoInterfaceSpring;
import dto.CardStatusDto;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import mapper.CardStatusMapper;
import model.CardStatus;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CardStatusDtoSpringService {
    private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;

    public CardStatusDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
        this.cardStatusDao = cardStatusDao;
    }

//    @Transactional
//    public Optional<CardStatusDto> save(CardStatusDto dto) {
//        if (cardStatusDao.findByValue(dto.getCardStatusName()).isPresent()) {
//            return Optional.empty();
//        }
//        CardStatus entity = CardStatusMapper.toEntity(dto);
//        CardStatus savedEntity = cardStatusDao.insert(entity);
//        return Optional.ofNullable(CardStatusMapper.toDto(savedEntity));
//    }
//
//    public Optional<CardStatusDto> getById(Long id) {
//        return cardStatusDao.findById(id)
//                .map(CardStatusMapper::toDto);
//    }


    @Transactional
    public Optional<CardStatusDto> save(CardStatusDto dto) {
        // Проверяем, есть ли уже сущность с таким именем
        Optional<CardStatus> existing = cardStatusDao.findByValue(dto.getCardStatusName());
        if (existing.isPresent()) {
            log.info("CardStatus '{}' уже существует", dto.getCardStatusName());
            return Optional.of(CardStatusMapper.toDto(existing.get()));
        }

        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующая сущность
        dto.setId(null);

        // Маппим в entity и сохраняем
        CardStatus entity = CardStatusMapper.toEntity(dto);
        CardStatus saved = cardStatusDao.insert(entity);

        return Optional.of(CardStatusMapper.toDto(saved));
    }

//    public List<CardStatusDto> findAll() {
//       return cardStatusDao.findAll();
//    }

    public List<CardStatusDto> findAll() {
        return cardStatusDao.findAll().stream()
                .map(CardStatusMapper::toDto)
                .collect(Collectors.toList());
    }




//    @Transactional
//    public Optional<CardStatusDto> save(CardStatusDto dto) {
//        Optional<CardStatus> existing = cardStatusDao.findByValue(dto.getCardStatusName());
//        if (existing.isPresent()) {
//            log.info("CardStatus '{}' уже существует", dto.getCardStatusName());
//            return Optional.of(CardStatusMapper.toDto(existing.get()));
//        }
//
//        CardStatus entity = CardStatusMapper.toEntity(dto);
//        CardStatus saved = cardStatusDao.insert(entity);
//        return Optional.of(CardStatusMapper.toDto(saved));
//    }
//
//    public List<CardStatusDto> getAll() {
//        List<CardStatusDto> cardStatusDtoList = new ArrayList<>();
//        try {
//        return cardStatusDao.findAll()
//                .stream()
//                .map(CardStatusMapper::toDto)
//                .collect(Collectors.toList());
//        } catch (PersistenceException e) {
//            // Можно ловить org.hibernate.exception.SQLGrammarException
//            //throw new TableNotFoundException("Таблица card_status не найдена", e);
//            log.error(e.getMessage());
//            return cardStatusDtoList;
//        }
//
//    }

//    @Transactional
//    public Optional<CardStatusDto> update(CardStatusDto dto) {
//        //Optional<CardStatus> existingEntity = cardStatusDao.findById(dto.getId());
//        Optional<CardStatus> existingEntity = cardStatusDao.findByValue(dto.getCardStatusName());
//        if (existingEntity.isEmpty()) {
//            return Optional.empty();
//        }
//        CardStatus updatedEntity = cardStatusDao.update(CardStatusMapper.toEntity(dto));
//        return Optional.ofNullable(CardStatusMapper.toDto(updatedEntity));
//    }

    @Transactional
    public Optional<CardStatusDto> update(CardStatusDto dto) {
        if (dto.getId() == null) {//если id не задан
            log.info("CardStatus must have an ID");
            //throw new IllegalArgumentException("ID должен быть задан для обновления");
            return Optional.empty();
        }

        Optional<CardStatus> existingEntityOpt = cardStatusDao.findById(dto.getId());
        if (existingEntityOpt.isEmpty()) {//если id не нашел
            log.info("CardStatus with ID = {} not found", dto.getId());
            return Optional.empty();
        }

        CardStatus existingEntity = existingEntityOpt.get();
        existingEntity.setCardStatusName(dto.getCardStatusName());

        return Optional.of(CardStatusMapper.toDto(existingEntity));
    }

    @Transactional
    public boolean delete(Long id) {
        return cardStatusDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
        return cardStatusDao.deleteAll();
    }

//    public Optional<CardStatusDto> getByName(String name) {
//        return cardStatusDao.findByValue(name)
//                .map(CardStatusMapper::toDto);
//    }

    public Optional<CardStatusDto> getByValue(String name) {
        return cardStatusDao.findByValue(name)
                .map(CardStatusMapper::toDto);
    }

    @Transactional
    public boolean dropTable() {
        return cardStatusDao.dropTable();
    }
}
