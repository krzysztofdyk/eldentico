package com.example.eldentico.user.service;

import com.example.eldentico.transfer.entity.TransferEntity;
import com.example.eldentico.transfer.repository.TransferRepository;
import com.example.eldentico.user.dto.UserRequestDto;
import com.example.eldentico.user.dto.UserResponseDto;
import com.example.eldentico.user.entity.UserEntity;
import com.example.eldentico.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TransferRepository transferRepository;

    public List<UserResponseDto> findAllUsers() {
        List<UserEntity> userEntity = userRepository.findAll();
        log.info("Find user finished.");
        return mapEntityToDtoList(userEntity);
    }

    public Page<UserResponseDto> findAllUsersPaged(Pageable pageable) {
        Page<UserEntity> userEntity = userRepository.findAll(pageable);
        log.info("Find user finished.");
        return mapEntityToDtoListPaged(userEntity);
    }

    public List<UserResponseDto> mapEntityToDtoList(List<UserEntity> userEntityList){
        return userEntityList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public Page<UserResponseDto> mapEntityToDtoListPaged(Page<UserEntity> userEntityList){
        List<UserResponseDto> userEntityPage = userEntityList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userEntityPage);
    }

    public UserResponseDto mapEntityToDto(UserEntity userEntity){
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .city(userEntity.getCity())
                .transfersId(mapTransfersToLongList(userEntity))
                .transferList(userEntity.getTransferList())
                .build();
    }

    public List<Long> mapTransfersToLongList(UserEntity userEntity){
        if (userEntity.getTransferList() != null){
            return userEntity.getTransferList().stream()
                    .map(TransferEntity::getId)
                    .collect(Collectors.toList());
        }
        else {
            return List.of();
        }
    }

    public UserEntity mapDtoToEntity(UserRequestDto userRequestDto){
        return UserEntity.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .city(userRequestDto.getCity())
                .build();
    }

    public UserEntity createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = mapDtoToEntity(userRequestDto);
        userRepository.save(userEntity);
        log.info("Create user finished.");
        return userEntity;
    }

    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.getById(id);
        userRepository.delete(userEntity);
        log.info("Delete user finished.");
    }

    public UserEntity updateUser(Long id , UserRequestDto userRequestDto) {
        UserEntity userEntity = userRepository.getById(id);
        userEntity.setFirstName(userRequestDto.getFirstName());
        userEntity.setLastName(userRequestDto.getLastName());
        userEntity.setCity(userRequestDto.getCity());
        userRepository.save(userEntity);
        log.info("Update user finished.");
        return userEntity;
    }

    public List<UserEntity> findUsersAndTransfers() {
        List<UserEntity> allUserEntity = userRepository.findAll();
        List<Long> userEntityIds = allUserEntity.stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());
        List<TransferEntity> transferEntityList = transferRepository.findAllByUserEntityIdIn(userEntityIds);
        allUserEntity.forEach(userEntity-> userEntity.setTransferList(transferEntityList));
        return allUserEntity;
    }

    // extractTransfers(transferEntityList,userEntity.getId()))
    private List<TransferEntity> extractTransfers(List<TransferEntity> transfers , long id){
        return transfers.stream()
                .filter(transfer ->transfer.getId()==id)
                .collect(Collectors.toList());
    }

}
