package edu.hust.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
    CategoryEntity findCategoryEntityById(Long id);
    public CategoryEntity findCategoryByCode(String code);
    CategoryEntity findCategoryEntityByNameAndType(String name, String type);
}
