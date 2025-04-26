package service.spring;

import dao.DaoInterfaceSpring;
import dto.IssuingBankDto;
import mapper.IssuingBankMapper;
import model.IssuingBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssuingBankDtoSpringServiceTest {
    private DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;
    private IssuingBankDtoSpringService service;

    @BeforeEach
    void setUp() {
        issuingBankDao = mock(DaoInterfaceSpring.class);
        service = new IssuingBankDtoSpringService(issuingBankDao);
    }

    @Test
    void save_ShouldReturnExistingById() {
        IssuingBankDto dto = new IssuingBankDto(1L, "BIC", "BIN", "Abbr");
        IssuingBank entity = IssuingBankMapper.toEntity(dto);
        when(issuingBankDao.findById(1L)).thenReturn(Optional.of(entity));

        Optional<IssuingBankDto> result = service.save(dto);

        assertTrue(result.isPresent());
        assertEquals(dto.getId(), result.get().getId());
        verify(issuingBankDao, times(1)).findById(1L);
        verify(issuingBankDao, never()).insert(any());
    }

    @Test
    void save_ShouldReturnExistingByName() {
        IssuingBankDto dto = new IssuingBankDto(null, "BIC", "BIN", "Abbr");
        IssuingBank entity = IssuingBankMapper.toEntity(dto);
        when(issuingBankDao.findByValue("Abbr")).thenReturn(Optional.of(entity));

        Optional<IssuingBankDto> result = service.save(dto);

        assertTrue(result.isPresent());
        assertEquals(dto.getAbbreviatedName(), result.get().getAbbreviatedName());
        verify(issuingBankDao, times(1)).findByValue("Abbr");
        verify(issuingBankDao, never()).insert(any());
    }

    @Test
    void save_ShouldInsertNew() {
        IssuingBankDto dto = new IssuingBankDto(null, "BIC", "BIN", "Abbr");
        IssuingBank entity = IssuingBankMapper.toEntity(dto);
        when(issuingBankDao.findByValue("Abbr")).thenReturn(Optional.empty());
        when(issuingBankDao.insert(any())).thenReturn(entity);

        Optional<IssuingBankDto> result = service.save(dto);

        assertTrue(result.isPresent());
        assertEquals(dto.getAbbreviatedName(), result.get().getAbbreviatedName());
        verify(issuingBankDao).insert(any());
    }

    @Test
    void findById_ShouldReturnDto() {
        IssuingBank entity = new IssuingBank(1L, "BIC", "BIN", "Abbr");
        when(issuingBankDao.findById(1L)).thenReturn(Optional.of(entity));

        Optional<IssuingBankDto> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findAll_ShouldReturnList() {
        IssuingBank entity = new IssuingBank(1L, "BIC", "BIN", "Abbr");
        when(issuingBankDao.findAll()).thenReturn(List.of(entity));

        List<IssuingBankDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Abbr", result.get(0).getAbbreviatedName());
    }

    @Test
    void update_ShouldReturnEmptyIfIdIsNull() {
        IssuingBankDto dto = new IssuingBankDto(null, "BIC", "BIN", "Abbr");

        Optional<IssuingBankDto> result = service.update(dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void update_ShouldReturnEmptyIfNotFound() {
        IssuingBankDto dto = new IssuingBankDto(99L, "BIC", "BIN", "Abbr");
        when(issuingBankDao.findById(99L)).thenReturn(Optional.empty());

        Optional<IssuingBankDto> result = service.update(dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void update_ShouldUpdateAndReturnDto() {
        IssuingBankDto dto = new IssuingBankDto(1L, "newBIC", "newBIN", "newAbbr");
        IssuingBank existing = new IssuingBank(1L, "oldBIC", "oldBIN", "oldAbbr");

        when(issuingBankDao.findById(1L)).thenReturn(Optional.of(existing));
        when(issuingBankDao.update(any())).thenReturn(existing);

        Optional<IssuingBankDto> result = service.update(dto);

        assertTrue(result.isPresent());
        assertEquals("newAbbr", result.get().getAbbreviatedName());
        verify(issuingBankDao).update(any());
    }

    @Test
    void delete_ShouldReturnTrue() {
        when(issuingBankDao.delete(1L)).thenReturn(true);
        assertTrue(service.delete(1L));
    }

    @Test
    void deleteAll_ShouldReturnTrue() {
        when(issuingBankDao.deleteAll()).thenReturn(true);
        assertTrue(service.deleteAll());
    }

    @Test
    void dropTable_ShouldReturnTrue() {
        when(issuingBankDao.dropTable()).thenReturn(true);
        assertTrue(service.dropTable());
    }
}