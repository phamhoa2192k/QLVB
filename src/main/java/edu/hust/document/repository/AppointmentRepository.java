package edu.hust.document.repository;

import edu.hust.document.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.hust.document.entity.AppointmentEntity;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>{
	AppointmentEntity findAppointmentEntityById(Long id);

	@Query("select a from AppointmentEntity a inner join BaseDocumentEntity b on a.id = b.id where b.name like %?1%")
	List<AppointmentEntity> findAppointmentEntitiesByNameLike(String name);
}
