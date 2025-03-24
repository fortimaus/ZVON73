package com.example.zvon73.service;

import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.TempleOperatorRequest;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.Enums.Role;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TempleService {

    private final TempleRepository templeRepository;
    private final UserService userService;

    private boolean checkAdmin(){
        User currentUser = userService.getCurrentUser();
        return currentUser.getRole().equals(Role.ADMIN);
    }
    private boolean checkRinger(Temple temple){
        User currentUser = userService.getCurrentUser();
        return temple.checkRinger(currentUser.getId());
    }


    @Transactional
    public Temple create(TempleDto temple)
    {
        if(!checkAdmin())
            throw new RuntimeException("403: Not access");
        Temple newTemple = Temple.builder()
                .title(temple.getTitle())
                .address(temple.getAddress())
                .phone(temple.getPhone())
                .description(temple.getDescription())
                .image(temple.getImage())
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

        Temple currentTemple = findById(UUID.fromString(newTemple.getId()));
        if(!checkAdmin() || !checkRinger(currentTemple))
            throw new RuntimeException("403: Not access");

        if( !currentTemple.getAddress().equals(newTemple.getAddress()) )
            currentTemple.setAddress(newTemple.getAddress());
        if( !currentTemple.getPhone().equals(newTemple.getPhone()) )
            currentTemple.setPhone(newTemple.getPhone());
        if( !currentTemple.getTitle().equals(newTemple.getTitle()) )
            currentTemple.setTitle(newTemple.getTitle());
        if( !currentTemple.getDescription().equals(newTemple.getDescription()) )
            currentTemple.setDescription(newTemple.getDescription());


        return templeRepository.save(currentTemple);
    }

    @Transactional
    public MessageResponse delete(UUID id){
        try {
            Temple currentTemple = findById(id);

            if(!checkAdmin() || !checkRinger(currentTemple))
                throw new RuntimeException("403: Not access");

            templeRepository.delete(currentTemple);
            return new MessageResponse("Храм успешно удален", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<TempleDto> findAll(){
        return templeRepository.findAll().stream().map(TempleDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<TempleDto> findListByName(String name){
        return templeRepository.findListByName('%'+name+'%').stream().map(TempleDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TempleDto> findByUser(){
        User currentUser = userService.getCurrentUser();
        return templeRepository.findTemplesByRingersId(currentUser.getId()).stream().map(TempleDto::new).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<TempleDto> findByUser(User user){
        return templeRepository.findTemplesByRingersId(user.getId()).stream().map(TempleDto::new).collect(Collectors.toList());

  }


}
