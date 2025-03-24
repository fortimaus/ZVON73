package com.example.zvon73.service;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import com.example.zvon73.repository.BellRepository;
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
public class BellService {

    private final BellRepository bellRepository;
    private final BellTowerService bellTowerService;
    private final UserService userService;


    @Transactional
    public Bell create(BellDto bell)
    {
        User currentUser = userService.getCurrentUser();
        BellTower bellTower = bellTowerService.findById(UUID.fromString(bell.getBellTowerId()));

        if(!bellTower.getTemple().checkRinger(currentUser.getId()))
            throw new RuntimeException("403 : Not access");

        Bell newBell = Bell.builder()
                .title(bell.getTitle())
                .weight(bell.getWeight())
                .manufacturer(bell.getManufacturer())
                .image(bell.getImage())
                .sound(bell.getSound())
                .bellTower(bellTower)
                .build();
        return bellRepository.save(newBell);
    }

    @Transactional(readOnly = true)
    public Bell findById(UUID id){
        return bellRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Колокола с данным id не найден"));
    }


    @Transactional
    public Bell update(BellDto newBell)
    {
        User currentUser = userService.getCurrentUser();
        Bell currentBell = findById(UUID.fromString(newBell.getId()));

        if(!currentBell.getBellTower().getTemple().checkRinger(currentUser.getId()))
            throw new RuntimeException("403 : Not access");

        if( !currentBell.getTitle().equals(newBell.getTitle()) )
            currentBell.setTitle(newBell.getTitle());

        if( !currentBell.getManufacturer().equals(newBell.getManufacturer()) )
            currentBell.setManufacturer(newBell.getManufacturer());

        if( currentBell.getWeight() != (newBell.getWeight()) )
            currentBell.setWeight(newBell.getWeight());

        if( currentBell.getBellTower().getId() != (UUID.fromString(newBell.getBellTowerId()))) {
            BellTower newBellTower = bellTowerService.findById(UUID.fromString(newBell.getBellTowerId()));
            currentBell.setBellTower(newBellTower);
            }


        return bellRepository.save(currentBell);
    }

    @Transactional
    public MessageResponse madeCanned(UUID id){
        try {
            User currentUser = userService.getCurrentUser();
            Bell currentBell = findById(id);

            if(!currentBell.getBellTower().getTemple().checkRinger(currentUser.getId()))
                return new MessageResponse("", "403 : Not access");

            currentBell.setCanned(true);
            bellRepository.save(currentBell);
            return new MessageResponse("Колокол успешно списан", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    @Transactional
    public MessageResponse delete(UUID id){
        try {
            User currentUser = userService.getCurrentUser();
            Bell currentBell = findById(id);

            if(!currentBell.getBellTower().getTemple().checkRinger(currentUser.getId()))
                return new MessageResponse("", "403 : Not access");

            bellRepository.delete(currentBell);
            return new MessageResponse("Колокол успешно удалён", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<BellDto> findAll(){
        return bellRepository.findByCannedFalse().stream().map(BellDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BellDto> findByTemple(UUID uid){
        return bellRepository.findByTempleId(uid).stream().map(BellDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<BellDto> findByName(String name){
        return bellRepository.findListByName('%'+name+'%').stream().map(BellDto::new).collect(Collectors.toList());
    }


}
