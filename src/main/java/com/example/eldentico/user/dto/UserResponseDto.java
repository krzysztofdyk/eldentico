package com.example.eldentico.user.dto;

import com.example.eldentico.transfer.entity.TransferEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseDto {
    private long id;
    private String firstName;
    private String lastName;
    private String city;
    private List<Long> transfersId;
    private List<TransferEntity> transferList;
}
