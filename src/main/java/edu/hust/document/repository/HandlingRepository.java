package edu.hust.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.HandlingEntity;

@Repository
public interface HandlingRepository extends JpaRepository<HandlingEntity, Long>{

}
	
