package com.example.eldentico.transfer.controller;

import com.example.eldentico.transfer.dto.TransferRequestDto;
import com.example.eldentico.transfer.dto.TransferResponseDto;
import com.example.eldentico.transfer.entity.TransferEntity;
import com.example.eldentico.transfer.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransferController {

    @Autowired
    TransferService transferService;

    @GetMapping("/transfers")
    //@Operation(summary = "Find transfers.")
    @ResponseStatus(HttpStatus.OK)
    public List<TransferResponseDto> findTransfersByCurrency(@RequestParam(name = "name", required = false) String name) {
        return transferService.findAllTransfers(name);
    }

    @GetMapping("/transfers/{id}")
    //@Operation(summary = "Find transfer by ID.")
    @ResponseStatus(HttpStatus.OK)
    public TransferResponseDto findTransferById(@PathVariable(name = "id") Long id) {
        return transferService.findTransferById(id);
    }

    @PostMapping("/transfers")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createTransfer(@RequestBody TransferRequestDto transferRequestDto) {
        TransferEntity entity = transferService.createTransfer(transferRequestDto);
        //return transferService.mapDto(entity);
        return HttpStatus.CREATED;
    }

    @PutMapping("/transfers/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransferResponseDto updateTransfer(@PathVariable(name = "id") Long id, @RequestBody TransferRequestDto transferRequestDto) {
        TransferEntity entity = transferService.updateTransfer(id,transferRequestDto);
        return transferService.mapDto(entity);
    }

    @DeleteMapping("/transfers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteTransfer(@PathVariable Long id) {
        transferService.deleteTransfer(id);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public Long sumTransfers(){
        return transferService.sumTransfers();
    }

    @GetMapping("/statistics/{currency}")
    @ResponseStatus(HttpStatus.OK)
    public Long sumTransfersByCurrency(@PathVariable(name = "currency") String currency){
        return transferService.sumTransfersByCurrencyQuery(currency);
    }

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> typeCurrency(){
        return transferService.typeCurrency();
    }
}
