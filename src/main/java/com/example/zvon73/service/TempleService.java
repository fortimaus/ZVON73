package com.example.zvon73.service;

import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TempleService {

    private final TempleRepository templeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Temple create(Temple temple)
    {
        return templeRepository.save(temple);
    }

    @Transactional(readOnly = true)
    public Temple findById(UUID id){
        return templeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Храм с данным id не найден"));
    }

    @Transactional
    public Temple update(Temple newTemple)
    {
        Temple currentTemple = findById(newTemple.getId());
        if (!newTemple.equals(currentTemple))
        {
            if( !currentTemple.getAddress().equals(newTemple.getAddress()) )
                currentTemple.setAddress(newTemple.getAddress());

            if( !currentTemple.getPhone().equals(newTemple.getPhone()) )
                currentTemple.setPhone(newTemple.getPhone());

            if( !currentTemple.getTitle().equals(newTemple.getTitle()) )
                currentTemple.setTitle(newTemple.getTitle());

            if( !currentTemple.getDescription().equals(newTemple.getDescription()) )
                currentTemple.setDescription(newTemple.getDescription());

            if( Arrays.equals(currentTemple.getImage(), newTemple.getImage()) )
                currentTemple.setImage(newTemple.getImage());
        }

        return templeRepository.save(currentTemple);
    }

    @Transactional
    public Temple delete(UUID id){
        Temple currentTemple = findById(id);
        templeRepository.delete(currentTemple);
        return currentTemple;
    }

    @Transactional(readOnly = true)
    public List<Temple> findAll(){
        return templeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Temple findByOperatorId(UUID id){
        return templeRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("У данного оператора нет храма"));

    }

    @Transactional
    public Temple updateOperator(UUID templeId, UUID operatorId){
        User currentUser = userRepository.findById(operatorId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Temple currentTemple = findById(templeId);
        if (!currentTemple.getUser().equals(currentUser))
            currentTemple.setUser(currentUser);
        return templeRepository.save(currentTemple);
    }
}
