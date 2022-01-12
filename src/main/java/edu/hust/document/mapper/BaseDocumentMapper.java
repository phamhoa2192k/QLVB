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
		baseDocumentEntity.setNumber(baseDocumentDTO.getNumber());
		baseDocumentEntity.setAgencyCode(baseDocumentDTO.getAgencyCode());
		baseDocumentEntity.setSymbol(baseDocumentDTO.getSymbol());
		baseDocumentEntity.setIssuanceTime(baseDocumentDTO.getIssuanceTime());
		baseDocumentEntity.setStatus(baseDocumentDTO.getStatus());
		baseDocumentEntity.setNumberOfPage(baseDocumentDTO.getNumberOfPage());
		baseDocumentEntity.setFile(baseDocumentDTO.getFile());
		
		return baseDocumentEntity;
	}
	
	public BaseDocumentEntity toEntity(BaseDocumentEntity baseDocumentEntity, BaseDocumentDTO baseDocumentDTO) {		
		baseDocumentEntity.setName(baseDocumentDTO.getName());
		baseDocumentEntity.setContent(baseDocumentDTO.getContent());
		baseDocumentEntity.setNumber(baseDocumentDTO.getNumber());
		baseDocumentEntity.setAgencyCode(baseDocumentDTO.getAgencyCode());
		baseDocumentEntity.setSymbol(baseDocumentDTO.getSymbol());
		baseDocumentEntity.setIssuanceTime(baseDocumentDTO.getIssuanceTime());
		baseDocumentEntity.setStatus(baseDocumentDTO.getStatus());
		baseDocumentEntity.setNumberOfPage(baseDocumentDTO.getNumberOfPage());
		baseDocumentEntity.setFile(baseDocumentDTO.getFile());
		
		return baseDocumentEntity;
	}
}
