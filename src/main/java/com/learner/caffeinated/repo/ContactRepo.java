package com.learner.caffeinated.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learner.caffeinated.entity.Contact;
@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer>{

	Optional<Contact> findByEmail(String email);

}
