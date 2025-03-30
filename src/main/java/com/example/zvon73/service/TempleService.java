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
    private final CheckUserRole checkUserRole;


    @Transactional
    public TempleDto create(TempleDto temple)
    {
        Temple newTemple = Temple.builder()
                .title(temple.getTitle())
                .address(temple.getAddress())
                .phone(temple.getPhone())
                .description(temple.getDescription())
                .image(temple.getImage())
                .build();
        return new TempleDto(templeRepository.save(newTemple));
    }

    @Transactional(readOnly = true)
    public Temple findById(UUID id){
        return templeRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public TempleDto update(TempleDto newTemple)
    {

        Temple currentTemple = findById(UUID.fromString(newTemple.getId()));

        if(currentTemple ==  null)
            return new TempleDto();
        if(!checkUserRole.checkForAdminOrRingerTemple(currentTemple))
            return new TempleDto();

        if( !currentTemple.getAddress().equals(newTemple.getAddress()) )
            currentTemple.setAddress(newTemple.getAddress());
        if( !currentTemple.getPhone().equals(newTemple.getPhone()) )
            currentTemple.setPhone(newTemple.getPhone());
        if( !currentTemple.getTitle().equals(newTemple.getTitle()) )
            currentTemple.setTitle(newTemple.getTitle());
        if( !currentTemple.getDescription().equals(newTemple.getDescription()) )
            currentTemple.setDescription(newTemple.getDescription());

        currentTemple.setImage(newTemple.getImage());

        return new TempleDto(templeRepository.save(currentTemple));
    }

    @Transactional
    public MessageResponse delete(UUID id){
        Temple currentTemple = findById(id);
        if(currentTemple ==  null)
            return new MessageResponse("", "Храм не найден");
        if(!currentTemple.getBellTowers().isEmpty())
            return new MessageResponse("", "В данном храме есть колокольни.");
        if(!checkUserRole.checkForAdminOrRingerTemple(currentTemple))
            return new MessageResponse("", "Not Access");
        templeRepository.delete(currentTemple);
        return new MessageResponse("Храм успешно удален", "");
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



}
