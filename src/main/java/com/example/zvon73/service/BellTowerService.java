package com.example.zvon73.service;

import com.example.zvon73.DTO.BellTowerDto;
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

@Service
@RequiredArgsConstructor
public class BellTowerService {


    private final BellTowerRepository bellTowerRepository;
    private final TempleService templeService;

    private void validate(BellTowerDto bellTower){
        if(bellTower.getTitle().isEmpty() || bellTower.getTitle().length() < 5 || bellTower.getTitle().length() >100)
            throw new ValidateException("Некорректное название колокольни");
        if(bellTower.getTemple().toString().isEmpty())
            throw new ValidateException("Некорректной номер храма колокольни");
    }

    @Transactional
    public BellTower create(BellTowerDto bellTower)
    {
        validate(bellTower);
        Temple temple = templeService.findById(bellTower.getTemple());
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
        BellTower currentBellTower = findById(newBellTower.getId());

            if( !currentBellTower.getTitle().equals(newBellTower.getTitle()) )
                currentBellTower.setTitle(newBellTower.getTitle());

            if( !currentBellTower.getTemple().equals(newBellTower.getTemple()) ) {
                Temple newTemple = templeService.findById(newBellTower.getTemple());
                currentBellTower.setTemple(newTemple);
            }


        return bellTowerRepository.save(currentBellTower);
    }

    @Transactional
    public BellTower delete(UUID id){
        BellTower currentBellTower = findById(id);
        bellTowerRepository.delete(currentBellTower);
        return currentBellTower;
    }

    @Transactional(readOnly = true)
    public List<BellTower> findAll(){
        return bellTowerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BellTower> findAllByTemple(UUID id){
        Temple currenttemple = templeService.findById(id);
        return bellTowerRepository.findByTemple(currenttemple)
                .orElseThrow(() -> new NotFoundException("Колокольня у храма с данным id не найден"));
    }
}
