package edu.hust.document.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "document")
public class DocumentEntity {
	
	@Id
    private Long id;

	@OneToOne
    @JoinColumn(name = "id")
	private BaseDocumentEntity baseDocumentEntity;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "security_level")
    private String securityLevel;

    @Column(name = "urgency_level")
    private String urgencyLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_department_id")
    private DepartmentEntity department;

}
