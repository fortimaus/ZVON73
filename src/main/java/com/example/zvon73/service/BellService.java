package com.example.zvon73.service;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.*;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.entity.Enums.Role;
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
    private final ManufacturerService manufacturerService;
    private final UserService userService;

    private boolean checkUser(Temple temple){
        User currentUser = userService.getCurrentUser();
        return temple.checkRinger(currentUser.getId()) || currentUser.getRole().equals(Role.ADMIN);
    }

    @Transactional
    public BellDto create(BellDto bell)
    {
        BellTower bellTower = bellTowerService.findById(UUID.fromString(bell.getBellTowerId()));
        Manufacturer manufacturer = manufacturerService.findById(UUID.fromString(bell.getManufacturerId()));
        if(!checkUser(bellTower.getTemple()))
            return new BellDto();

        Bell newBell = Bell.builder()
                .title(bell.getTitle())
                .weight(bell.getWeight())
                .diameter(bell.getDiameter())
                .manufacturer(manufacturer)
                .image(bell.getImage())
                .sound(bell.getSound())
                .bellTower(bellTower)
                .build();
        return new BellDto(bellRepository.save(newBell));
    }

    @Transactional(readOnly = true)
    public Bell findById(UUID id){
        return bellRepository.findById(id).orElse(null);
    }


    @Transactional
    public BellDto update(BellDto newBell)
    {
        Bell currentBell = findById(UUID.fromString(newBell.getId()));

        if(currentBell == null)
            return new BellDto();

        if(!checkUser(currentBell.getBellTower().getTemple()))
            return new BellDto();

        if( !currentBell.getTitle().equals(newBell.getTitle()) )
            currentBell.setTitle(newBell.getTitle());

        if( currentBell.getManufacturer().getId() != UUID.fromString(newBell.getManufacturerId()) ) {
            Manufacturer manufacturer = manufacturerService.findById(UUID.fromString(newBell.getManufacturerId()));
            currentBell.setManufacturer(manufacturer);
        }

        if( currentBell.getWeight() != (newBell.getWeight()) )
            currentBell.setWeight(newBell.getWeight());

        if( currentBell.getBellTower().getId() != (UUID.fromString(newBell.getBellTowerId()))) {
            BellTower newBellTower = bellTowerService.findById(UUID.fromString(newBell.getBellTowerId()));
            currentBell.setBellTower(newBellTower);
            }


        return new BellDto(bellRepository.save(currentBell));
    }

    @Transactional
    public MessageResponse madeCanned(UUID id){
        Bell currentBell = findById(id);
        if(currentBell == null)
            return new MessageResponse("", "Колокол не найден");
        if(!checkUser(currentBell.getBellTower().getTemple()))
            return new MessageResponse("", "403 : Not access");
        currentBell.setCanned(true);
        bellRepository.save(currentBell);
        return new MessageResponse("Колокол успешно списан", "");

    }

    @Transactional
    public MessageResponse recover(UUID id){
        Bell currentBell = findById(id);
        if(currentBell == null)
            return new MessageResponse("", "Колокол не найден");
        if(!checkUser(currentBell.getBellTower().getTemple()))
            return new MessageResponse("", "403 : Not access");
        currentBell.setCanned(false);
        bellRepository.save(currentBell);
        return new MessageResponse("Колокол успешно списан", "");
    }

    @Transactional
    public MessageResponse delete(UUID id){
        Bell currentBell = findById(id);
        if(currentBell == null)
            return new MessageResponse("", "Колокол не найден");
        if(!checkUser(currentBell.getBellTower().getTemple()))
            return new MessageResponse("", "403 : Not access");
        bellRepository.delete(currentBell);
        return new MessageResponse("Колокол успешно удалён", "");

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
