package com.example.repo;

import com.example.repo.entity.TransactionDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDetail, Long> {

    List<TransactionDetail> findByCustomerEmailIgnoreCase(String email);

}
