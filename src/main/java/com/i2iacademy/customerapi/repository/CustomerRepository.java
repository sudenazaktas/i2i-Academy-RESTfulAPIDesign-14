package com.i2iacademy.customerapi.repository;

import com.i2iacademy.customerapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring Data JPA automatically implements basic CRUD operations
// (save, findById, findAll, deleteById, etc.) based on this interface.
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Custom query method: Spring Data JPA generates the query
    // automatically based on the method name.
    Optional<Customer> findByEmail(String email);
}