package com.example.springproject.repository;


import com.example.springproject.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findByEmail(String email);
    Optional<User> getUserById(Long id);
}
