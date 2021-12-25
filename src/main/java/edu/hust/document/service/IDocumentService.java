package edu.hust.document.service;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.entity.DocumentEntity;

public interface IDocumentService {
	
	public DocumentEntity createDocument(DocumentDTO documentDTO);
	public DocumentEntity updateDocument(DocumentDTO documentDTO, Long documentId);
	public DocumentEntity findDocumentById(Long id);

}
