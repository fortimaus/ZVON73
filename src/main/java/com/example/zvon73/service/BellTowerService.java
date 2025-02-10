package com.example.zvon73.service;

import com.example.zvon73.DTO.BellTowerDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Temple;
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

    private void validate(BellTowerDto bellTower){
        if(bellTower.getTitle().isEmpty() || bellTower.getTitle().length() < 5 || bellTower.getTitle().length() >100)
            throw new ValidateException("Некорректное название колокольни");
        if(bellTower.getTempleId().toString().isEmpty())
            throw new ValidateException("Некорректной номер храма колокольни");
    }

    @Transactional
    public BellTower create(BellTowerDto bellTower)
    {
        validate(bellTower);
        Temple temple = templeService.findById(UUID.fromString(bellTower.getTempleId()));
        BellTower newBellTower = BellTower.builder()
                .title(bellTower.getTitle())
                .temple(temple)
                .build();
        return bellTowerRepository.save(newBellTower);
    }

    @Transactional(readOnly = true)
    public BellTower findById(UUID id){
        return bellTowerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Колокольня с данным id не найден"));
    }

    @Transactional
    public BellTower update(BellTowerDto newBellTower)
    {
        validate(newBellTower);
        BellTower currentBellTower = findById(UUID.fromString(newBellTower.getId()));

            if( !currentBellTower.getTitle().equals(newBellTower.getTitle()) )
                currentBellTower.setTitle(newBellTower.getTitle());

            if( !currentBellTower.getTemple().equals(UUID.fromString(newBellTower.getTempleId())) ) {
                Temple newTemple = templeService.findById(UUID.fromString(newBellTower.getTempleId()));
                currentBellTower.setTemple(newTemple);
            }


        return bellTowerRepository.save(currentBellTower);
    }

    @Transactional
    public MessageResponse delete(UUID id){
        try {
            BellTower currentBellTower = findById(id);
            bellTowerRepository.delete(currentBellTower);
            return new MessageResponse("Колокольня успешно удалена", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
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
