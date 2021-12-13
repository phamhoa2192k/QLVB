package edu.hust.document.service.impl;

import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.repository.HandlingRepository;
import edu.hust.document.service.IHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandlingService implements IHandlingService {
    @Autowired
    private HandlingRepository handlingRepository;

    @Override
    public List<HandlingEntity> findByAppointmentId(Long id){
        List<HandlingEntity> handlingEntities = null;
        handlingEntities = handlingRepository.findAllByBaseDocumentId(id);

        return handlingEntities;
    }
}
