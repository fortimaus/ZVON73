package com.example.zvon73.service;

import com.example.zvon73.DTO.UserRecordDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.UserRecordRequest;
import com.example.zvon73.entity.User;
import com.example.zvon73.entity.UserRecord;
import com.example.zvon73.repository.UserRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRecordService {
    private final UserRecordRepository userRecordRepository;
    private final UserService userService;
    public UserRecord save(UserRecord record) {
        return userRecordRepository.save(record);
    }


    public List<UserRecordDto> getAllRecordsList(PageRequest request){
        Page<UserRecord> userRecordsPage = userRecordRepository.findAll(request);
        return userRecordsPage.getContent().stream().map(UserRecordDto::new).collect(Collectors.toList());
    }

    public MessageResponse create(UserRecordRequest request) {
        try {
            var user = userService.getCurrentUser();
            var record = UserRecord.builder()
                    .name(request.getName())
                    .user(user)
                    .record(request.getRecord())
                    .build();
            save(record);
            return new MessageResponse("Запись сохранена", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    public void delete(UserRecordDto record){
        userRecordRepository.deleteById(UUID.fromString(record.getId()));
    }
}
