package com.example.zvon73.DTO;

import com.example.zvon73.entity.UserRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordDto {
    private String id;
    private String name;
    private byte[] record;

    public UserRecordDto(UserRecord record){
        this.id = record.getId().toString();
        this.name = record.getName();
        this.record = record.getRecord();
    }
}
