package com.caffeinated.caffeinatedpersonaservice.repo;

import java.util.Optional;

import com.caffeinated.caffeinatedpersonaservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByEmail(String email);

}
