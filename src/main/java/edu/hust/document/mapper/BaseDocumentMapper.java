package edu.hust.document.mapper;

import org.springframework.stereotype.Component;

import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.entity.BaseDocumentEntity;

@Component
public class BaseDocumentMapper {

	public BaseDocumentEntity toEntity(BaseDocumentDTO baseDocumentDTO) {
		BaseDocumentEntity baseDocumentEntity = new BaseDocumentEntity();
		
		baseDocumentEntity.setCode(baseDocumentDTO.getCode());
		baseDocumentEntity.setName(baseDocumentDTO.getName());
		baseDocumentEntity.setContent(baseDocumentDTO.getContent());
		baseDocumentEntity.setAgencyCode(baseDocumentDTO.getAgencyCode());
		baseDocumentEntity.setNumber(baseDocumentDTO.getNumber());
		baseDocumentEntity.setSignerName(baseDocumentDTO.getSignerName());
		baseDocumentEntity.setSignerPosition(baseDocumentDTO.getSignerPosition());
		baseDocumentEntity.setIssuanceTime(baseDocumentDTO.getIssuanceTime());
		baseDocumentEntity.setForwardTime(baseDocumentDTO.getForwardTime());
		baseDocumentEntity.setOtherInfo(baseDocumentDTO.getOtherInfo());
		
		return baseDocumentEntity;
	}
	
	public BaseDocumentEntity toEntity(BaseDocumentEntity baseDocumentEntity, BaseDocumentDTO baseDocumentDTO) {		
		baseDocumentEntity.setCode(baseDocumentDTO.getCode());
		baseDocumentEntity.setName(baseDocumentDTO.getName());
		baseDocumentEntity.setContent(baseDocumentDTO.getContent());
		baseDocumentEntity.setAgencyCode(baseDocumentDTO.getAgencyCode());
		baseDocumentEntity.setNumber(baseDocumentDTO.getNumber());
		baseDocumentEntity.setSignerName(baseDocumentDTO.getSignerName());
		baseDocumentEntity.setSignerPosition(baseDocumentDTO.getSignerPosition());
		baseDocumentEntity.setIssuanceTime(baseDocumentDTO.getIssuanceTime());
		baseDocumentEntity.setForwardTime(baseDocumentDTO.getForwardTime());
		baseDocumentEntity.setOtherInfo(baseDocumentDTO.getOtherInfo());
		
		return baseDocumentEntity;
	}
}
