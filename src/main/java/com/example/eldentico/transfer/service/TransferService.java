package com.example.eldentico.transfer.service;

import com.example.eldentico.transfer.dto.TransferRequestDto;
import com.example.eldentico.transfer.dto.TransferResponseDto;
import com.example.eldentico.transfer.entity.TransferEntity;
import com.example.eldentico.transfer.enums.ExceptionInfo;
import com.example.eldentico.transfer.exception.TransferException;
import com.example.eldentico.transfer.repository.TransferRepository;
import com.example.eldentico.user.entity.UserEntity;
import com.example.eldentico.user.repository.UserRepository;
import com.example.eldentico.word.WordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final WordService wordService;


    public List<TransferResponseDto> findAllTransfers(String name) {
        log.info("Find transfers was run.");
        if (name != null && !name.isBlank()) {
            return mapDtoList(transferRepository.findByName(name));
        } else {
            return mapDtoList(transferRepository.findAll());
        }
    }

    public TransferResponseDto findTransferById(Long id) {
        log.info("Find transfer by Id finished.");
        return mapDto(transferRepository.getById(id));
    }

    public TransferEntity createTransfer(TransferRequestDto transferRequestDto){
        LocalDate localDateNow = LocalDate.now();
        LocalTime localTimeNow = LocalTime.now();
        if (transferRequestDto.getAmount() == 0) {
            throw new TransferException(ExceptionInfo.AMOUNT_WAS_PROVIDED_AS_ZERO.name(), HttpStatus.BAD_REQUEST);
        }
        UserEntity transferOwner = userRepository.getById(transferRequestDto.getUserId());
        TransferEntity transfer = mapEntity(transferRequestDto, transferOwner,localDateNow,localTimeNow);
        transferRepository.save(transfer);
        log.info("Create transfer finished.");
        return transfer;
    }

    public TransferEntity updateTransfer(Long id, TransferRequestDto transferRequestDto) {
        TransferEntity transfer = transferRepository.getById(id);
        UserEntity userOwner = userRepository.findById(transferRequestDto.getUserId()).orElseThrow();
        transfer.setAmount(transferRequestDto.getAmount());
        transfer.setName(transferRequestDto.getName());
        transfer.setUserEntity(userOwner);
        transferRepository.save(transfer);
        log.info("Update transfer finished.");
        return transfer;
    }

    public void deleteTransfer(Long id) throws TransferException {
        transferRepository.findById(id).orElseThrow(() -> {
            throw new TransferException(ExceptionInfo.ID_WAS_NOT_FOUND.name(), HttpStatus.NOT_FOUND);
        });
        transferRepository.deleteById(id);
        log.info("Delete transfer finished.");
        // return ResponseEntity.noContent().build();
    }

    private TransferEntity mapEntity(TransferRequestDto transferRequestDto, UserEntity userEntity, LocalDate localDate,LocalTime localTime) {
        return TransferEntity.builder()
                .amount(transferRequestDto.getAmount())
                .name(transferRequestDto.getName())
                .userEntity(userEntity)
                .localDate(localDate)
                .localTime(localTime)
                .build();
    }

    public TransferResponseDto mapDto(TransferEntity transferEntity) {
        return TransferResponseDto.builder()
                .id(transferEntity.getId())
                .amount(transferEntity.getAmount())
                .name(transferEntity.getName())
                .userId(transferEntity.getUserEntity().getId())
                .build();
    }

    public List<TransferResponseDto> mapDtoList(List<TransferEntity> transferEntityList) {
        return transferEntityList.stream()
                .map(this::mapDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long sumTransfers() {
        log.info("Get total sum finished.");
        return transferRepository.amountQuery();
    }

    public Long sumTransfersByCurrencyQuery(String currency) {
        log.info("Get sum by currency finished.");
        return transferRepository.amountByCurrencyQuery(currency);
    }

    public Set<String> typeCurrency() {
        List<TransferEntity> transfers = transferRepository.findAll();
        Set<String> typeCurrency = transfers.stream()
                .map(TransferEntity::getName)
                .collect(Collectors.toSet());
        return typeCurrency;
    }

    public boolean hasTransferFilledName (TransferEntity transferEntity){
        return Optional.ofNullable(transferEntity.getName()).isEmpty();
    }





}
