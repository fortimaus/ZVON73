package com.example.zvon73.service;

import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.repository.UserRepository;
import com.example.zvon73.service.Exceptions.ValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TempleService {

    private final TempleRepository templeRepository;
    private final UserService userService;

    private void validate(TempleDto temple){
        if(temple.getTitle().isEmpty() || temple.getTitle().length() < 5 || temple.getTitle().length() >100)
            throw new ValidateException("Некорректное название храма");
        if(temple.getAddress().isEmpty() || temple.getAddress().length() < 5 || temple.getAddress().length() > 100)
            throw new ValidateException("Некорректный адрес храма");
        if(temple.getPhone().isEmpty() || temple.getPhone().length() > 12 || temple.getPhone().length() < 11 )
            throw new ValidateException("Некорректный телефон храма");
        if(temple.getDescription().isEmpty())
            throw new ValidateException("Некорректное описание храма");
        if(temple.getImage().length == 0)
            throw new ValidateException("Некорректное изображение храма");
    }

    @Transactional
    public Temple create(TempleDto temple)
    {   validate(temple);
        Temple newTemple = Temple.builder()
                .title(temple.getTitle())
                .address(temple.getAddress())
                .phone(temple.getPhone())
                .description(temple.getDescription())
                .image(temple.getImage())
                .user(null)
                .build();
        return templeRepository.save(newTemple);
    }

    @Transactional(readOnly = true)
    public Temple findById(UUID id){
        return templeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Храм с данным id не найден"));
    }

    @Transactional
    public Temple update(TempleDto newTemple)
    {
        validate(newTemple);
        Temple currentTemple = findById(newTemple.getId());

            if( !currentTemple.getAddress().equals(newTemple.getAddress()) )
                currentTemple.setAddress(newTemple.getAddress());

            if( !currentTemple.getPhone().equals(newTemple.getPhone()) )
                currentTemple.setPhone(newTemple.getPhone());

            if( !currentTemple.getTitle().equals(newTemple.getTitle()) )
                currentTemple.setTitle(newTemple.getTitle());

            if( !currentTemple.getDescription().equals(newTemple.getDescription()) )
                currentTemple.setDescription(newTemple.getDescription());

            if( Arrays.equals(currentTemple.getImage(), newTemple.getImage()) )
                currentTemple.setImage(newTemple.getImage());

        return templeRepository.save(currentTemple);
    }

    @Transactional
    public Temple delete(UUID id){
        Temple currentTemple = findById(id);
        templeRepository.delete(currentTemple);
        return currentTemple;
    }

    @Transactional(readOnly = true)
    public List<Temple> findAll(){
        return templeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Temple findByUser(){
        User currentUser = userService.getCurrentUser();
        return templeRepository.findByUser(currentUser)
                .orElseThrow(() -> new NotFoundException("У данного оператора нет храма"));

    }

    @Transactional(readOnly = true)
    public List<Temple> findAllByIdNot(UUID id){
        return templeRepository.findAllByIdNot(id);

    }

    @Transactional
    public Temple updateOperator(UUID templeId, UUID userId){
        User currentUser = userService.findById(userId);
        Temple currentTemple = findById(templeId);
        if (!currentTemple.getUser().equals(currentUser))
            currentTemple.setUser(currentUser);
        return templeRepository.save(currentTemple);
    }
}
