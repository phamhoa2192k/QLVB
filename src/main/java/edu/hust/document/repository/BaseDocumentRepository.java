package edu.hust.document.repository;

import edu.hust.document.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.BaseDocumentEntity;

import java.util.List;

@Repository
public interface BaseDocumentRepository extends JpaRepository<BaseDocumentEntity, Long> {
    List<BaseDocumentEntity> findAllByCategory(CategoryEntity categoryEntity);

    BaseDocumentEntity findBaseDocumentEntityById(Long id);
	
}
