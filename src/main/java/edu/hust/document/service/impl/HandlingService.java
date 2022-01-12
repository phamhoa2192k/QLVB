package edu.hust.document.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.repository.HandlingRepository;
import edu.hust.document.service.IHandlingService;

@Service
public class HandlingService implements IHandlingService {
	
	@Autowired
    private HandlingRepository handlingRepository;

	@Override
	public List<HandlingEntity> findByAppointmentId(Long id) {
		List<HandlingEntity> handlingEntities = null;
        handlingEntities = handlingRepository.findAllByBaseDocumentId(id);

        return handlingEntities;
	}

	@Override
	public List<HandlingEntity> findByDocumentId(Long id) {
		return handlingRepository.findAllByBaseDocumentId(id);
	}

}
