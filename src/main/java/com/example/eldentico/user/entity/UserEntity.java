package com.example.eldentico.user.entity;

import com.example.eldentico.transfer.entity.TransferEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String city;

    @OneToMany
    @JoinColumn(name = "user_entity_id")
    private List<TransferEntity> transferList;

}
