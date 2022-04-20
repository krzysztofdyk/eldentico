package com.example.eldentico.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferRequestDto {
    private String name;
    private long amount;
    private long userId;
}
