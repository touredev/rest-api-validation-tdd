package com.jeanmtoure.learningjava.restapivalidation.repository;

import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
}
