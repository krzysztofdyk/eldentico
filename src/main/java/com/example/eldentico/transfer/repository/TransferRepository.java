package com.example.eldentico.transfer.repository;

import com.example.eldentico.transfer.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity,Long> {

    List<TransferEntity> findByName (String name);
    List<TransferEntity> findAllByUserEntityIdIn (List<Long> userEntityId);

/*    @Query(value =
            "SELECT DISTINCT name" +
            "FROM TransferEntity" , nativeQuery = true )
    List<String> find();*/

    @Query(value =
            "SELECT sum(te.amount)" +
                    "FROM TransferEntity te", nativeQuery = true)
    Long amountQuery();

    @Query (value =
            "SELECT sum(te.amount)" +
                    "FROM TransferEntity te" +
                    "WHERE te.name = :currency" , nativeQuery = true)
    Long amountByCurrencyQuery(String currency);
}
