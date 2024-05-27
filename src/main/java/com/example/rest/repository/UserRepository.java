package com.example.rest.repository;

import com.example.rest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, String> {

    User findByMail(String mail);

    Boolean existsByMailAndPassword(String mail,String password);

    Optional<User> findById(String id);


    Optional<User> findByUsername(String userName);
}

