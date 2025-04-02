package com.example.zvon73.service;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.DTO.NoticeDto;
import com.example.zvon73.DTO.OrderDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.*;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.entity.Enums.TypeNotice;
import com.example.zvon73.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserService userService;
    private final TempleService templeService;
    private final CheckUserRole checkUserRole;



    @Transactional
    public NoticeDto create(NoticeDto noticeDto){

        User curUser = userService.getCurrentUser();
        Temple curTemple = templeService.findById(UUID.fromString(noticeDto.getTemple()));

        if(!checkUserRole.checkForAdminOrRingerTemple(curTemple))
            return new NoticeDto();

        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .manufacturer(noticeDto.getManufacturer())
                .weight(noticeDto.getWeight())
                .diameter(noticeDto.getDiameter())
                .image(noticeDto.getImage())
                .description(noticeDto.getDescription())
                .type(TypeNotice.valueOf(noticeDto.getType()))
                .date(LocalDateTime.now())
                .user(curUser)
                .temple(curTemple)
                .build();

        return new NoticeDto(noticeRepository.save(notice));
    }
    @Transactional(readOnly = true)
    public Notice findById(UUID id){
        return noticeRepository.findById(id)
                .orElse(null);
    }
    @Transactional
    public NoticeDto update(NoticeDto noticeDto)
    {

        Notice curNotice = findById(UUID.fromString(noticeDto.getId()));

        if(curNotice == null)
            return new NoticeDto();
        Temple curTemple = templeService.findById(UUID.fromString(noticeDto.getTemple()));

        if(!checkUserRole.checkForAdminOrRingerTemple(curTemple) &&
                !checkUserRole.checkForAdminOrRingerTemple(curNotice.getTemple()))
            return new NoticeDto();

        curNotice.setTitle(noticeDto.getTitle());
        curNotice.setManufacturer(noticeDto.getManufacturer());
        curNotice.setWeight(noticeDto.getWeight());
        curNotice.setDiameter(noticeDto.getDiameter());
        curNotice.setImage(noticeDto.getImage());
        curNotice.setDescription(noticeDto.getDescription());

        curNotice.setTemple(curTemple);
        return new NoticeDto(noticeRepository.save(curNotice));
    }
    @Transactional
    public MessageResponse delete(UUID id){
        Notice currentNotice = findById(id);
        if(currentNotice == null)
            return new MessageResponse("", "Заявка не найдена");
        User currentUser = userService.getCurrentUser();
        if(!currentUser.getId().equals(currentNotice.getUser().getId()))
            return new MessageResponse("", "Not Access");
        noticeRepository.delete(currentNotice);
        return new MessageResponse("Заявка успешно удалёна", "");

    }

    @Transactional(readOnly = true)
    public List<NoticeDto> findMyNotice(){
        User currentUser = userService.getCurrentUser();
        return noticeRepository.findMyNotices(currentUser.getId()).stream().map(NoticeDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<NoticeDto> findGiveNotice(){
        return noticeRepository.findByType(TypeNotice.Give).stream().map(NoticeDto::new).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<NoticeDto> findTakeNotice(){
        return noticeRepository.findByType(TypeNotice.Take).stream().map(NoticeDto::new).collect(Collectors.toList());
    }
}
