package service.spring;

import dao.DaoInterfaceSpring;
import model.CardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardStatusSpringServiceTest {
    private DaoInterfaceSpring<Long, CardStatus> cardStatusDao;
    private CardStatusSpringService cardStatusService;

    @BeforeEach
    void setUp() {
        cardStatusDao = mock(DaoInterfaceSpring.class);
        cardStatusService = new CardStatusSpringService(cardStatusDao);
    }

    @Test
    void save_ShouldReturnEmptyIfAlreadyExists() {
        when(cardStatusDao.findByValue("ACTIVE")).thenReturn(Optional.of(new CardStatus()));

        Optional<CardStatus> result = cardStatusService.save("ACTIVE");

        assertTrue(result.isEmpty());
        verify(cardStatusDao).findByValue("ACTIVE");
        verify(cardStatusDao, never()).insert(any());
    }

    @Test
    void save_ShouldInsertIfNotExists() {
        CardStatus newCardStatus = new CardStatus();
        newCardStatus.setCardStatusName("NEW");

        when(cardStatusDao.findByValue("NEW")).thenReturn(Optional.empty());
        when(cardStatusDao.insert(any(CardStatus.class))).thenReturn(newCardStatus);

        Optional<CardStatus> result = cardStatusService.save("NEW");

        assertTrue(result.isPresent());
        assertEquals("NEW", result.get().getCardStatusName());
        verify(cardStatusDao).insert(any(CardStatus.class));
    }

    @Test
    void getById_ShouldReturnCardStatus() {
        CardStatus status = new CardStatus();
        status.setId(1L);

        when(cardStatusDao.findById(1L)).thenReturn(Optional.of(status));

        Optional<CardStatus> result = cardStatusService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getAll_ShouldReturnList() {
        List<CardStatus> statuses = List.of(new CardStatus(), new CardStatus());
        when(cardStatusDao.findAll()).thenReturn(statuses);

        List<CardStatus> result = cardStatusService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedCardStatus() {
        CardStatus original = new CardStatus();
        original.setId(1L);
        original.setCardStatusName("OLD");

        CardStatus updated = new CardStatus();
        updated.setId(1L);
        updated.setCardStatusName("UPDATED");

        when(cardStatusDao.findById(1L)).thenReturn(Optional.of(original));
        when(cardStatusDao.update(original)).thenReturn(updated);

        Optional<CardStatus> result = cardStatusService.update(original);

        assertTrue(result.isPresent());
        assertEquals("UPDATED", result.get().getCardStatusName());
    }

    @Test
    void update_ShouldReturnEmptyIfNotFound() {
        CardStatus update = new CardStatus();
        update.setId(1L);

        when(cardStatusDao.findById(1L)).thenReturn(Optional.empty());

        Optional<CardStatus> result = cardStatusService.update(update);

        assertTrue(result.isEmpty());
        verify(cardStatusDao, never()).update(any());
    }

    @Test
    void delete_ShouldReturnTrue() {
        when(cardStatusDao.delete(1L)).thenReturn(true);

        boolean result = cardStatusService.delete(1L);

        assertTrue(result);
    }

    @Test
    void deleteAll_ShouldReturnTrue() {
        when(cardStatusDao.deleteAll()).thenReturn(true);

        boolean result = cardStatusService.deleteALL();

        assertTrue(result);
    }

    @Test
    void getByName_ShouldReturnCardStatus() {
        CardStatus status = new CardStatus();
        status.setCardStatusName("ACTIVE");

        when(cardStatusDao.findByValue("ACTIVE")).thenReturn(Optional.of(status));

        Optional<CardStatus> result = cardStatusService.getByName("ACTIVE");

        assertTrue(result.isPresent());
        assertEquals("ACTIVE", result.get().getCardStatusName());
    }

    @Test
    void dropTable_ShouldReturnTrue() {
        when(cardStatusDao.dropTable()).thenReturn(true);

        boolean result = cardStatusService.dropTable();

        assertTrue(result);
    }

}