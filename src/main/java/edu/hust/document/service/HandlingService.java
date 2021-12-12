package edu.hust.document.service;

import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.repository.HandlingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandlingService {
    @Autowired
    private HandlingRepository handlingRepository;

    public List<HandlingEntity> findByAppointmentId(Long id){
        List<HandlingEntity> handlingEntities = null;
        handlingEntities = handlingRepository.findAllByBaseDocumentId(id);

        return handlingEntities;
    }
}
