package edu.hust.document.mapper;

import org.springframework.stereotype.Component;

import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.entity.HandlingEntity;

@Component
public class HandlingMapper {

	public HandlingEntity toEntity(HandlingDTO handlingDTO) {
		HandlingEntity entity = new HandlingEntity();
		entity.setAction(handlingDTO.getAction());
		entity.setTime(handlingDTO.getTime());
		entity.setNote(handlingDTO.getNote());
		return entity;
	}
}
