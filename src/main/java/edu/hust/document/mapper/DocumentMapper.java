package edu.hust.document.mapper;

import org.springframework.stereotype.Component;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.entity.DocumentEntity;

@Component
public class DocumentMapper {
	
	public DocumentEntity toEntity(DocumentDTO documentDTO) {
		DocumentEntity documentEntity = new DocumentEntity();
		documentEntity.setDeadline(documentDTO.getDeadline());
		documentEntity.setAttachedDocument(documentDTO.getAttachedDocument());
		documentEntity.setSecurityLevel(documentDTO.getSecurityLevel());
		documentEntity.setUrgencyLevel(documentDTO.getUrgencyLevel());
		//documentEntity.setStatus(documentDTO.getStatus());
		
		return documentEntity;
	}
	
	public DocumentEntity toEntity(DocumentEntity documentEntity, DocumentDTO documentDTO) {
		documentEntity.setDeadline(documentDTO.getDeadline());
		documentEntity.setAttachedDocument(documentDTO.getAttachedDocument());
		documentEntity.setSecurityLevel(documentDTO.getSecurityLevel());
		documentEntity.setUrgencyLevel(documentDTO.getUrgencyLevel());
	//	documentEntity.setStatus(documentDTO.getStatus());
		
		return documentEntity;
	}

}
