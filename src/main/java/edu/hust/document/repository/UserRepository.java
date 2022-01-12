package edu.hust.document.repository;


import edu.hust.document.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findUserByUserName(String username);

	UserEntity findUserEntityById(Long id);
	
	@Query("SELECT u FROM UserEntity u WHERE u.department.id = ?1")
	List<UserEntity> findAllByDepartmentId(Long departmentId);
	
	@Query("SELECT u FROM UserEntity u WHERE u.department.id = ?1 AND position = 'manager'")
	UserEntity findManagerByDepartmentId(Long departmentId);
	
	@Query("SELECT u FROM UserEntity u WHERE position = 'manager'")
	List<UserEntity> findAllManagers();
}
