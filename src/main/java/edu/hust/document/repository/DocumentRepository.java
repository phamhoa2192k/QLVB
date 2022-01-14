package edu.hust.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.DocumentEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    DocumentEntity findDocumentEntityById(Long id);

    @Query("select d from DocumentEntity d " +
            "inner join BaseDocumentEntity b on d.id = b.id " +
            "inner join CategoryEntity c on c.id = b.category.id where c.name like ?1")
    List<DocumentEntity> findDocumentEntityByCategaryName(String categoryName);

    @Query("select d from DocumentEntity d " +
            "inner join BaseDocumentEntity b on d.id = b.id " +
            "inner join CategoryEntity c on c.id = b.category.id " +
            "where c.name like ?1 and b.name like %?2%")
    List<DocumentEntity> findDocumentEntityByCategaryNameAndDocumentName(String categoryName, String documentName);

    @Query("select d from DocumentEntity d " +
            "inner join BaseDocumentEntity b on d.id = b.id " +
            "inner join CategoryEntity c on c.id = b.category.id " +
            "where c.name like ?1 and b.status like ?2")
    List<DocumentEntity> findDocumentEntityByCategaryNameAndBaseDocumentEntityStatus(String categoryName, String status);

    @Query("select d from DocumentEntity d " +
            "inner join BaseDocumentEntity b on d.id = b.id " +
            "inner join CategoryEntity c on c.id = b.category.id " +
            "where c.name like ?1 and b.status like ?2 and b.assignee.id = ?3")
    List<DocumentEntity> findDocumentEntityByCategaryNameAndBaseDocumentEntityStatusAndUserId(String categoryName, String status,
                                                                                              Long userId);

    @Query("select d from DocumentEntity d "
            + "inner join BaseDocumentEntity b on d.id = b.id "
            + " where b.assignee.id = ?1")
    List<DocumentEntity> findDocumentEntityByAssignee(Long assigneeId);

    @Query("select d from DocumentEntity d " +
            "inner join BaseDocumentEntity b on d.id = b.id " +
            "inner join CategoryEntity c on c.id = b.category.id " +
            "inner join DepartmentEntity dp on dp.id = d.department.id " +
            "where c.name like ?1 and b.status like ?2 and d.department.id = ?3")
    List<DocumentEntity> findAllByCategoryAndStatusAndDepartment(String categoryName, String status, Long departmentId);

}