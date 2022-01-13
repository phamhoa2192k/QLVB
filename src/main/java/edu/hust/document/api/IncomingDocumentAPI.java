package edu.hust.document.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hust.document.api.input.DocumentAssignment;
import edu.hust.document.api.input.FileUpload;
import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.entity.DocumentEntity;
import edu.hust.document.service.IncomingDocumentInterface;

@CrossOrigin
@RestController
@RequestMapping("/api/incomingdocument")
public class IncomingDocumentAPI {
	
	@Autowired
	private IncomingDocumentInterface incomingDocumentService;
	
	@PostMapping
	public DocumentEntity createDocument(@RequestBody DocumentDTO documentDTO) {
		return incomingDocumentService.createDocument(documentDTO);
	}
	
	@PutMapping(value = "/{documentId}")
	public DocumentEntity updateDocument(
			@RequestBody DocumentDTO documentDTO, 
			@PathVariable("documentId") Long documentId) {
		return incomingDocumentService.updateDocument(documentDTO, documentId);
	}
	
	
	@GetMapping("/{documentId}")
	public DocumentEntity getDocumentById(@PathVariable @Valid @NotNull @Min(1) Long documentId) {
		return incomingDocumentService.findDocumentById(documentId);
	}
	
	@GetMapping("/assign/{assigneeId}")
	public List<DocumentEntity> getDocumentByAssigneeId(@PathVariable @Valid @NotNull @Min(1) Long assigneeId) {
		return incomingDocumentService.findDocumentByAssigneeId(assigneeId);
	}
	
	@PostMapping(value = "/assign")
	public void assign(@RequestBody DocumentAssignment documentAssignment) {
		incomingDocumentService.assignDocumentForUser(documentAssignment.getDocumentId(), documentAssignment.getAssigneeId());
	}
	
	@PostMapping(value = "/handle")
	public void handle(@RequestBody HandlingDTO handlingDTO) {
		incomingDocumentService.handleDocument(handlingDTO);
	}
	
	@PostMapping(value = "/upload")
	public void uploadFile(@RequestBody FileUpload fileUpload) {
		incomingDocumentService.uploadFile(fileUpload);
	}

	@GetMapping("/all")
	public List<DocumentEntity> getAll() {
		return incomingDocumentService.findAll();
	}

}
