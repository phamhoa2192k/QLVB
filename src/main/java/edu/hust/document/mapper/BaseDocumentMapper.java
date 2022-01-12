package edu.hust.document.mapper;

import org.springframework.stereotype.Component;

import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.entity.BaseDocumentEntity;

@Component
public class BaseDocumentMapper {

	public BaseDocumentEntity toEntity(BaseDocumentDTO baseDocumentDTO) {
		BaseDocumentEntity baseDocumentEntity = new BaseDocumentEntity();

		baseDocumentEntity.setName(baseDocumentDTO.getName());
		baseDocumentEntity.setContent(baseDocumentDTO.getContent());
		baseDocumentEntity.setAgencyCode(baseDocumentDTO.getAgencyCode());
		baseDocumentEntity.setNumber(baseDocumentDTO.getNumber());
		baseDocumentEntity.setIssuanceTime(baseDocumentDTO.getIssuanceTime());
		
		return baseDocumentEntity;
	}
	
	public BaseDocumentEntity toEntity(BaseDocumentEntity baseDocumentEntity, BaseDocumentDTO baseDocumentDTO) {
		baseDocumentEntity.setName(baseDocumentDTO.getName());
		baseDocumentEntity.setContent(baseDocumentDTO.getContent());
		baseDocumentEntity.setAgencyCode(baseDocumentDTO.getAgencyCode());
		baseDocumentEntity.setNumber(baseDocumentDTO.getNumber());
		baseDocumentEntity.setIssuanceTime(baseDocumentDTO.getIssuanceTime());
		
		return baseDocumentEntity;
	}
}
