package com.bookstore.api.repository;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.api.model.Authors;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, String>{

	List<Authors> findAll();
	public Authors findByNameIn(List<String> name);
}
