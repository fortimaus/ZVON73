package com.example.zvon73.service;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.repository.BellRepository;
import com.example.zvon73.repository.BellTowerRepository;
import com.example.zvon73.service.Exceptions.ValidateException;
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
    private final BellTowerService bellTowerService;
    @Transactional
    public Bell create(BellDto bell)
    {

        validate(bell);

        BellTower bellTower = bellTowerService.findById(bell.getBellTower());

        Bell newBell = Bell.builder()
                .title(bell.getTitle())
                .weight(bell.getWeight())
                .manufacturer(bell.getManufacturer())
                .image(bell.getImage())
                .sound(bell.getSound())
                .bellTower(bellTower)
                .status(BellStatus.Accepted)
                .build();
        return bellRepository.save(newBell);
    }

    @Transactional(readOnly = true)
    public Bell findById(UUID id){
        return bellRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Колокола с данным id не найден"));
    }
    private void validate(BellDto bell){
        if(bell.getTitle().isEmpty() || bell.getTitle().length() < 5 || bell.getTitle().length() >100)
            throw new ValidateException("Некорректное название колокола");
        if(bell.getWeight() < 0)
            throw new ValidateException("Некорректной вес колокола");
        if(bell.getManufacturer().isEmpty() || bell.getManufacturer().length() < 3 || bell.getManufacturer().length() > 100)
            throw new ValidateException("Некорректное название производителя");
        if(bell.getImage().length == 0)
            throw new ValidateException("Некорректное изображение колокола");
        if(bell.getSound().length == 0)
            throw new ValidateException("Некорректный звук колокола");
        if(bell.getBellTower().toString().isEmpty())
            throw new ValidateException("Некорректный номер колокольни");
    }

    @Transactional
    public Bell update(BellDto newBell)
    {
        Bell currentBell = findById(newBell.getId());
        validate(newBell);
            if( !currentBell.getTitle().equals(newBell.getTitle()) )
                currentBell.setTitle(newBell.getTitle());

            if( !currentBell.getManufacturer().equals(newBell.getManufacturer()) )
                currentBell.setManufacturer(newBell.getManufacturer());

            if( currentBell.getWeight() != (newBell.getWeight()) )
                currentBell.setWeight(newBell.getWeight());

            if( currentBell.getBellTower().getId() != (newBell.getBellTower())) {
                BellTower newBellTower = bellTowerService.findById(newBell.getBellTower());
                currentBell.setBellTower(newBellTower);
            }

            if ( !Arrays.equals(currentBell.getSound(), newBell.getSound()) )
                currentBell.setSound(newBell.getSound());

            if( !Arrays.equals(currentBell.getImage(), newBell.getImage()) )
                currentBell.setImage(newBell.getImage());

        return bellRepository.save(currentBell);
    }

    @Transactional
    public Bell delete(UUID id){
        Bell currentBell = findById(id);
        bellRepository.delete(currentBell);
        return currentBell;
    }

    @Transactional(readOnly = true)
    public List<Bell> findAll(){
        return bellRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Bell> findByTemple(UUID uid){
        return bellRepository.findByTempleId(uid)
                .orElseThrow(() -> new NotFoundException("Колоколов у храма с таким id не найден"));
    }

    @Transactional
    public Bell updateStatusAccepted(UUID id){
        Bell currentBell = findById(id);
        currentBell.setStatus(BellStatus.Accepted);
        return bellRepository.save(currentBell);
    }

    @Transactional
    public Bell updateStatusInPath(UUID id){
        Bell currentBell = findById(id);
        currentBell.setStatus(BellStatus.In_path);
        return bellRepository.save(currentBell);
    }
}
