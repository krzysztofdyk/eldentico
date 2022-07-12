package com.example.eldentico.word;


import com.example.eldentico.transfer.entity.TransferEntity;
import com.example.eldentico.transfer.repository.TransferRepository;
import com.example.eldentico.user.entity.UserEntity;
import com.example.eldentico.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WordController {

    UserRepository userRepository;
    WordService wordService;
    TransferRepository transferRepository;

    @GetMapping("/users/history/{transfer_id}")
    public HttpStatus exportTransferByTransferId(@PathVariable(name = "transfer_id") Long transfer_id){
        TransferEntity transferEntity = transferRepository.getById(transfer_id);
        UserEntity userEntity = userRepository.getById(transferEntity.getUserEntity().getId());
        wordService.createSingleWord(transferEntity, userEntity);
        return HttpStatus.CREATED;
    }

    @GetMapping("/users/{user_id}/history")
    public HttpStatus exportAllTransfersByUserId(@PathVariable(name = "user_id") Long user_id){
        UserEntity user = userRepository.getById(user_id);
        wordService.createWordByUser(user);
        return HttpStatus.CREATED;
    }
}
