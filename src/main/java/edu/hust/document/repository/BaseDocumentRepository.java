package edu.hust.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.BaseDocumentEntity;

@Repository
public interface BaseDocumentRepository extends JpaRepository<BaseDocumentEntity, Long> {
	
}
