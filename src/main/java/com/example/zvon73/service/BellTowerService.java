package com.example.zvon73.service;

import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.repository.BellTowerRepository;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.repository.UserRepository;
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

    @Transactional
    public BellTower create(BellTower bellTower)
    {
        return bellTowerRepository.save(bellTower);
    }

    @Transactional(readOnly = true)
    public BellTower findById(UUID id){
        return bellTowerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Колокольня с данным id не найден"));
    }

    @Transactional
    public BellTower update(BellTower newBellTower)
    {
        BellTower currentBellTower = findById(newBellTower.getId());
        if (!newBellTower.equals(currentBellTower))
        {
            if( !currentBellTower.getTitle().equals(newBellTower.getTitle()) )
                currentBellTower.setTitle(newBellTower.getTitle());

            if( !currentBellTower.getTemple().equals(newBellTower.getTemple()) ) {
                currentBellTower.setTemple(newBellTower.getTemple());
            }
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
    public List<BellTower> findAllByTempleId(UUID id){
        return bellTowerRepository.findByTempleId(id)
                .orElseThrow(() -> new NotFoundException("Колокольня у храма с данным id не найден"));
    }
}
