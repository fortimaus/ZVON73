package com.example.zvon73;

import com.example.zvon73.DTO.ManufacturerDto;
import com.example.zvon73.entity.Manufacturer;
import com.example.zvon73.repository.ManufacturerRepository;
import com.example.zvon73.service.ManufacturerService;
import com.example.zvon73.service.Exceptions.ValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;

    private ManufacturerDto validManufacturerDto;
    private Manufacturer manufacturer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID manufacturerId = UUID.randomUUID();
        validManufacturerDto = ManufacturerDto.builder()
                .id(manufacturerId.toString())
                .title("тайтл")
                .address("адресс")
                .phone("123456789012")
                .build();

        manufacturer = Manufacturer.builder()
                .id(manufacturerId)
                .title("тайтл")
                .address("адресс")
                .phone("123456789012")
                .build();
    }

    @Test
    void create_ValidData_Success() {
        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(manufacturer);

        Manufacturer createdManufacturer = manufacturerService.create(validManufacturerDto);

        assertNotNull(createdManufacturer);
        assertEquals(validManufacturerDto.getTitle(), createdManufacturer.getTitle());
        assertEquals(validManufacturerDto.getAddress(), createdManufacturer.getAddress());
        assertEquals(validManufacturerDto.getPhone(), createdManufacturer.getPhone());

        verify(manufacturerRepository, times(1)).save(any(Manufacturer.class));
    }

    @Test
    void create_InvalidTitle_ThrowsValidateException() {
        validManufacturerDto.setTitle("");

        assertThrows(ValidateException.class, () -> manufacturerService.create(validManufacturerDto));
        verify(manufacturerRepository, never()).save(any());
    }

    @Test
    void findById_ValidId_Success() {
        when(manufacturerRepository.findById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));

        Manufacturer foundManufacturer = manufacturerService.findById(manufacturer.getId());

        assertNotNull(foundManufacturer);
        assertEquals(manufacturer.getId(), foundManufacturer.getId());
        verify(manufacturerRepository, times(1)).findById(manufacturer.getId());
    }

    @Test
    void findById_InvalidId_ThrowsNotFoundException() {
        when(manufacturerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> manufacturerService.findById(UUID.randomUUID()));
        verify(manufacturerRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void findAll_ReturnsListOfManufacturers() {
        Manufacturer manufacturer2 = Manufacturer.builder()
                .id(UUID.randomUUID())
                .title("Manufacturer 2")
                .address("Address 2")
                .phone("987654321098")
                .build();

        when(manufacturerRepository.findAll()).thenReturn(Arrays.asList(manufacturer, manufacturer2));

        List<ManufacturerDto> manufacturers = manufacturerService.findAll();

        assertNotNull(manufacturers);
        assertEquals(2, manufacturers.size());
    }

    @Test
    void update_ValidData_Success() {
        when(manufacturerRepository.findById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));
        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(manufacturer);

        validManufacturerDto.setTitle("Новый тайтл");
        Manufacturer updatedManufacturer = manufacturerService.update(validManufacturerDto);

        assertNotNull(updatedManufacturer);
        assertEquals("Новый тайтл", updatedManufacturer.getTitle());
        verify(manufacturerRepository, times(1)).save(any(Manufacturer.class));
    }

    @Test
    void update_InvalidId_ThrowsNotFoundException() {
        when(manufacturerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> manufacturerService.update(validManufacturerDto));
        verify(manufacturerRepository, never()).save(any());
    }

    @Test
    void delete_ValidId_Success() {
        when(manufacturerRepository.findById(manufacturer.getId())).thenReturn(Optional.of(manufacturer));
        doNothing().when(manufacturerRepository).delete(manufacturer);

        manufacturerService.delete(manufacturer.getId());

        verify(manufacturerRepository, times(1)).delete(manufacturer);
    }

    @Test
    void delete_InvalidId_ThrowsNotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(manufacturerRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            Manufacturer manufacturer = manufacturerService.findById(invalidId);
            manufacturerService.delete(manufacturer.getId());
        });

        verify(manufacturerRepository, never()).delete(any());
    }

    @Test
    void findListByTitle_ValidTitle_ReturnsList() {
        when(manufacturerRepository.findListByTitle("тайтл")).thenReturn(List.of(manufacturer));

        List<ManufacturerDto> manufacturers = manufacturerService.findListByTitle("тайтл");

        assertNotNull(manufacturers);
        assertEquals(1, manufacturers.size());
        assertEquals(manufacturer.getTitle(), manufacturers.get(0).getTitle());
    }
}