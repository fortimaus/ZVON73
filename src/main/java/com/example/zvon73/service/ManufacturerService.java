package com.example.zvon73.service;

import com.example.zvon73.DTO.ManufacturerDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.Manufacturer;
import com.example.zvon73.repository.ManufacturerRepository;
import com.example.zvon73.service.Exceptions.ValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    private void validate(ManufacturerDto manufacturer) {
        if (manufacturer.getTitle() == null || manufacturer.getTitle().isEmpty() || manufacturer.getTitle().length() < 3 || manufacturer.getTitle().length() > 100)
            throw new ValidateException("Некорректное название производителя");
        if (manufacturer.getAddress() == null || manufacturer.getAddress().isEmpty() || manufacturer.getAddress().length() < 5 || manufacturer.getAddress().length() > 200)
            throw new ValidateException("Некорректный адрес производителя");
        if (manufacturer.getPhone() == null || manufacturer.getPhone().isEmpty() || manufacturer.getPhone().length() != 12)
            throw new ValidateException("Некорректный телефон производителя");
        if (manufacturer.getDescription() != null && (manufacturer.getDescription().length() < 10 || manufacturer.getDescription().length() > 500))
            throw new ValidateException("Некорректное описание производителя");
    }

    @Transactional
    public Manufacturer create(ManufacturerDto manufacturerDto) {
        validate(manufacturerDto);
        Manufacturer newManufacturer = Manufacturer.builder()
                .title(manufacturerDto.getTitle())
                .address(manufacturerDto.getAddress())
                .phone(manufacturerDto.getPhone())
                .description(manufacturerDto.getDescription())
                .build();
        return manufacturerRepository.save(newManufacturer);
    }

    @Transactional(readOnly = true)
    public Manufacturer findById(UUID id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Производитель с данным id не найден"));
    }

    @Transactional(readOnly = true)
    public List<ManufacturerDto> findAll() {
        return manufacturerRepository.findAll().stream()
                .map(ManufacturerDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Manufacturer update(ManufacturerDto manufacturerDto) {
        validate(manufacturerDto);
        Manufacturer currentManufacturer = findById(UUID.fromString(manufacturerDto.getId()));
        if (!currentManufacturer.getTitle().equals(manufacturerDto.getTitle()))
            currentManufacturer.setTitle(manufacturerDto.getTitle());
        if (!currentManufacturer.getAddress().equals(manufacturerDto.getAddress()))
            currentManufacturer.setAddress(manufacturerDto.getAddress());
        if (!currentManufacturer.getPhone().equals(manufacturerDto.getPhone()))
            currentManufacturer.setPhone(manufacturerDto.getPhone());
        if (manufacturerDto.getDescription() != null && !manufacturerDto.getDescription().equals(currentManufacturer.getDescription()))
            currentManufacturer.setDescription(manufacturerDto.getDescription());
        return manufacturerRepository.save(currentManufacturer);
    }

    @Transactional
    public MessageResponse delete(UUID id) {
        try {
            Manufacturer currentManufacturer = findById(id);
            manufacturerRepository.delete(currentManufacturer);
            return new MessageResponse("Производитель успешно удален", "");
        } catch (Exception e) {
            return new MessageResponse("", e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<ManufacturerDto> findListByTitle(String title) {
        return manufacturerRepository.findListByTitle(title).stream()
                .map(ManufacturerDto::new)
                .collect(Collectors.toList());
    }
}