package com.learner.caffeinated.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learner.caffeinated.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
