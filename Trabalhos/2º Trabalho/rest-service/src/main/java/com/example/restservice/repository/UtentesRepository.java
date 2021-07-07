package com.example.restservice.repository;

import com.example.restservice.model.Utentes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtentesRepository extends JpaRepository<Utentes, Long>{
	
	Optional<Utentes> findById(Long id);

}
