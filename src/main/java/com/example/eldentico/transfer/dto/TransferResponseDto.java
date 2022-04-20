package com.example.eldentico.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferResponseDto {
    private long id;
    private String name;
    private long amount;
    private long userId;
}
