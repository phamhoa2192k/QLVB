package edu.hust.document.service;

import edu.hust.document.entity.HandlingEntity;

import java.util.List;

public interface IHandlingService {
    List<HandlingEntity> findByAppointmentId(Long id);
}
