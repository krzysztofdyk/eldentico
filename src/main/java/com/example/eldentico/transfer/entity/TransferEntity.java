package com.example.eldentico.transfer.entity;

import com.example.eldentico.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(scale = 4)
    private long amount;
    private LocalDate localDate;
    private LocalTime localTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "user_entity_id")
    private UserEntity userEntity;
}
