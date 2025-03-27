package com.example.zvon73.service;

import com.example.zvon73.DTO.ManufacturerDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.Manufacturer;
import com.example.zvon73.entity.User;
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
    private final UserService userService;

  
    private boolean checkUser()
    {
        User currentUser = userService.getCurrentUser();
        return currentUser.getRole().equals(Role.ADMIN);
    }

    @Transactional
    public ManufacturerDto create(ManufacturerDto manufacturerDto) {

        if(!checkUser())
            return new ManufacturerDto();

        Manufacturer newManufacturer = Manufacturer.builder()
                .title(manufacturerDto.getTitle())
                .address(manufacturerDto.getAddress())
                .phone(manufacturerDto.getPhone())
                .description(manufacturerDto.getDescription())
                .build();
        return new ManufacturerDto(manufacturerRepository.save(newManufacturer));
    }

    @Transactional(readOnly = true)
    public Manufacturer findById(UUID id) {
        return manufacturerRepository.findById(id)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ManufacturerDto> findAll() {
        return manufacturerRepository.findAll().stream()
                .map(ManufacturerDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ManufacturerDto update(ManufacturerDto manufacturerDto) {

        if(!checkUser())
            return new ManufacturerDto();

        Manufacturer currentManufacturer = findById(UUID.fromString(manufacturerDto.getId()));

        if(currentManufacturer == null)
            return new ManufacturerDto();

        if (!currentManufacturer.getTitle().equals(manufacturerDto.getTitle()))
            currentManufacturer.setTitle(manufacturerDto.getTitle());
        if (!currentManufacturer.getAddress().equals(manufacturerDto.getAddress()))
            currentManufacturer.setAddress(manufacturerDto.getAddress());
        if (!currentManufacturer.getPhone().equals(manufacturerDto.getPhone()))
            currentManufacturer.setPhone(manufacturerDto.getPhone());
        if (manufacturerDto.getDescription() != null && !manufacturerDto.getDescription().equals(currentManufacturer.getDescription()))
            currentManufacturer.setDescription(manufacturerDto.getDescription());
        return new ManufacturerDto(manufacturerRepository.save(currentManufacturer));
    }

    @Transactional
    public MessageResponse delete(UUID id) {
        if(!checkUser())
            return new MessageResponse("", "Not Access");
        Manufacturer currentManufacturer = findById(id);
        if(currentManufacturer == null)
            return new MessageResponse("", "Производитель не найден");
        manufacturerRepository.delete(currentManufacturer);
        return new MessageResponse("Производитель успешно удален", "");

    }

    @Transactional(readOnly = true)
    public List<ManufacturerDto> findListByTitle(String title) {
        return manufacturerRepository.findListByTitle(title).stream()
                .map(ManufacturerDto::new)
                .collect(Collectors.toList());
    }
}