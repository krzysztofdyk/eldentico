package com.example.eldentico.file.service;

import ch.qos.logback.core.util.FileSize;
import com.example.eldentico.file.dto.FileResponseDto;
import com.example.eldentico.file.entity.FileEntity;
import com.example.eldentico.file.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please select a file to save");
        }
        if (file.getSize() >= FileSize.MB_COEFFICIENT * 5){
            throw new IllegalArgumentException("The file size cannot exceed 5 Mb");
        }

        FileEntity fileEntity = FileEntity.builder()
                .name(file.getOriginalFilename())
                .fileData(file.getBytes())
                .build();
        fileRepository.save(fileEntity);
        log.info("File upload finished.");
    }

    @Transactional
    public byte[] downloadFile(Long id) {
        FileEntity fileEntity = fileRepository.getById(id);
        log.info("File download finished.");
        mapDto(fileEntity);
        return fileEntity.getFileData();
    }

    private FileResponseDto mapDto(FileEntity fileEntity){
        return FileResponseDto.builder()
                .id(fileEntity.getId())
                .fileName(fileEntity.getName())
                .file(fileEntity.getFileData())
                .build();
    }
}
