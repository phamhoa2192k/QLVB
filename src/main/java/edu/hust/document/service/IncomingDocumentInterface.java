package edu.hust.document.service;

import java.util.List;

import edu.hust.document.api.input.FileUpload;
import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.entity.DocumentEntity;
import edu.hust.document.entity.HandlingEntity;

public interface IncomingDocumentInterface {
	
	public DocumentEntity createDocument(DocumentDTO documentDTO);
	public DocumentEntity updateDocument(DocumentDTO documentDTO, Long documentId);
	public DocumentEntity findDocumentById(Long id);
	public List<DocumentEntity> findDocumentByAssigneeId(Long assigneeId);
	public void assignDocumentForUser(Long documentId, Long userId);
	public void handleDocument(HandlingDTO handlingDTO);
	public List<HandlingEntity> getAllHandlingsByDocumentId(Long documentId);
	public void uploadFile(FileUpload fileUpload);
	public List<DocumentEntity> findAll();

}
