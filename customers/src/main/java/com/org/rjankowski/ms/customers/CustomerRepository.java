package com.org.rjankowski.ms.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//change to interface with extends JpaRepository
// add new required correct methods
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT distinct c FROM Customer c join fetch c.addresses where c.firstName = ?1 and c.lastName = ?2")
    List<Customer> findAllByFirstNameAndLastName(String f, String l);

    @Query("SELECT distinct c FROM Customer c join fetch c.addresses where c.firstName = ?1")
    List<Customer> findAllByFirstName(String f);

    @Query("SELECT distinct c FROM Customer c join fetch c.addresses where c.lastName = :l")
    List<Customer> findAllByLastName(String l);

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    <S extends Customer> S saveAndFlush(S customer);

    void delete(Customer customer);

    void deleteById(Long id);
}
