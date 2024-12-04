package com.example.zvon73.service;

import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.repository.BellRepository;
import com.example.zvon73.repository.BellTowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BellService {

    private final BellRepository bellRepository;

    @Transactional
    public Bell create(Bell bell)
    {
        return bellRepository.save(bell);
    }

    @Transactional(readOnly = true)
    public Bell findById(UUID id){
        return bellRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Колокола с данным id не найден"));
    }

    @Transactional(readOnly = true)
    public Bell update(Bell newBell)
    {
        Bell currentBell = findById(newBell.getId());
        if (!newBell.equals(currentBell))
        {
            if( !currentBell.getTitle().equals(newBell.getTitle()) )
                currentBell.setTitle(newBell.getTitle());

            if( !currentBell.getManufacturer().equals(newBell.getManufacturer()) )
                currentBell.setManufacturer(newBell.getManufacturer());

            if( currentBell.getWeight() != (newBell.getWeight()) )
                currentBell.setWeight(newBell.getWeight());

            if( currentBell.getBellTower() != (newBell.getBellTower()) )
                currentBell.setBellTower(newBell.getBellTower());

            if ( !Arrays.equals(currentBell.getSound(), newBell.getSound()) )
                currentBell.setSound(newBell.getSound());

            if( !Arrays.equals(currentBell.getImage(), newBell.getImage()) )
                currentBell.setImage(newBell.getImage());

        }

        return bellRepository.save(currentBell);
    }

    @Transactional(readOnly = true)
    public Bell delete(UUID id){
        Bell currentBell = findById(id);
        bellRepository.delete(currentBell);
        return currentBell;
    }

    @Transactional
    public List<Bell> findAll(){
        return bellRepository.findAll();
    }

    @Transactional
    public List<Bell> findByTemple(UUID uid){
        return bellRepository.findByTempleId(uid)
                .orElseThrow(() -> new NotFoundException("Колоколов у храма с таким id не найден"));
    }

    @Transactional(readOnly = true)
    public Bell updateStatusAccepted(UUID id){
        Bell currentBell = findById(id);
        currentBell.setStatus(BellStatus.Accepted);
        return bellRepository.save(currentBell);
    }

    @Transactional(readOnly = true)
    public Bell updateStatusInPath(UUID id){
        Bell currentBell = findById(id);
        currentBell.setStatus(BellStatus.In_path);
        return bellRepository.save(currentBell);
    }
}
