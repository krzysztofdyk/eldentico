package com.example.eldentico.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FileResponseDto {
    private long id;
    private String fileName;
    private byte[] file;
}
