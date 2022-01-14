package edu.hust.document.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.repository.HandlingRepository;
import edu.hust.document.service.IHandlingService;

@Service
public class HandlingService implements IHandlingService {
	
	@Autowired
    private HandlingRepository handlingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

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

	@Override
	public List<HandlingDTO> findHandlingDTOByDocumentId(Long id) {
		List<HandlingEntity> handlingEntities = handlingRepository.findAllByBaseDocumentId(id);

		List<HandlingDTO> handlingDTOList = new ArrayList<>();
		for (HandlingEntity handlingEntity: handlingEntities) {
			HandlingDTO handlingDTO = new HandlingDTO();
			handlingDTO.setId(handlingEntity.getId());
			handlingDTO.setAction(handlingEntity.getAction());
			handlingDTO.setNote(handlingEntity.getNote());
			handlingDTO.setTime(handlingEntity.getTime());

			handlingDTO.setHandlingUserName(handlingEntity.getUser().getFullName());
			handlingDTOList.add(handlingDTO);
		}
		return handlingDTOList;
	}

}
