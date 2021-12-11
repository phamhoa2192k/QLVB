package edu.hust.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.DepartmentEntity;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    @Query("select d from DepartmentEntity d where d.name like %?1%")
    List<DepartmentEntity> findDepartmentEntitiesByNameLike(String name);

    DepartmentEntity findDepartmentEntityById(Long id);
	
}
