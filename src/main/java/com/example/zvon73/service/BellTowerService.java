package com.example.zvon73.service;

import com.example.zvon73.DTO.BellTowerDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import com.example.zvon73.repository.BellTowerRepository;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.repository.UserRepository;
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
public class BellTowerService {


    private final BellTowerRepository bellTowerRepository;
    private final TempleService templeService;
    private final UserService userService;

    private boolean checkUser(Temple temple){
        User currentUser = userService.getCurrentUser();
        return temple.checkRinger(currentUser.getId()) || currentUser.getRole().equals(Role.ADMIN);
    }

    @Transactional
    public BellTowerDto create(BellTowerDto bellTower)
    {

        Temple temple = templeService.findById(UUID.fromString(bellTower.getTempleId()));

        if(!checkUser(temple))
            return new BellTowerDto();

        BellTower newBellTower = BellTower.builder()
                .title(bellTower.getTitle())
                .temple(temple)
                .build();
        return new BellTowerDto(bellTowerRepository.save(newBellTower));
    }

    @Transactional(readOnly = true)
    public BellTower findById(UUID id){
        return bellTowerRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public BellTowerDto update(BellTowerDto newBellTower)
    {

        BellTower currentBellTower = findById(UUID.fromString(newBellTower.getId()));

        if(currentBellTower == null)
            return new BellTowerDto();

        if(!checkUser(currentBellTower.getTemple()))
            return new BellTowerDto();

        if( !currentBellTower.getTitle().equals(newBellTower.getTitle()) )
            currentBellTower.setTitle(newBellTower.getTitle());
        if( !currentBellTower.getTemple().getId().equals(UUID.fromString(newBellTower.getTempleId())) ) {
            Temple newTemple = templeService.findById(UUID.fromString(newBellTower.getTempleId()));
            currentBellTower.setTemple(newTemple);
        }


        return new BellTowerDto(bellTowerRepository.save(currentBellTower));
    }

    @Transactional
    public MessageResponse delete(UUID id){
        BellTower currentBellTower = findById(id);
        if(currentBellTower == null)
            return new MessageResponse("", "Колокольня не найден");
        if(!checkUser(currentBellTower.getTemple()))
            return new MessageResponse("", "Not Access");
        bellTowerRepository.delete(currentBellTower);
        return new MessageResponse("Колокольня успешно удалена", "");

    }

    @Transactional(readOnly = true)
    public List<BellTowerDto> findAll(){
        return bellTowerRepository.findAll().stream().map(BellTowerDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<BellTowerDto> findAllByName(String name){
        return bellTowerRepository.findListByName('%'+name+'%').stream().map(BellTowerDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BellTowerDto> findAllByTemple(UUID id){
        Temple currentTemple = templeService.findById(id);
        return bellTowerRepository.findByTemple(currentTemple).stream().map(BellTowerDto::new).collect(Collectors.toList());
    }
}
