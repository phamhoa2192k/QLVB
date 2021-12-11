package edu.hust.document.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class AppointmentEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    BaseDocumentEntity baseDocumentEntity;

    @Column(name = "security_level")
    private String securityLevel;

    @Column(name = "urgency_level")
    private String urgencyLevel;

}
