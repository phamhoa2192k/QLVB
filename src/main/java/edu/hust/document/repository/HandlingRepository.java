package edu.hust.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.HandlingEntity;

import java.util.List;

@Repository
public interface HandlingRepository extends JpaRepository<HandlingEntity, Long>{

    @Query("select d from HandlingEntity d where d.baseDocument.id = ?1")
    List<HandlingEntity> findAllByBaseDocumentId(Long id);

}
	
