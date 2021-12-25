package edu.hust.document.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.entity.DocumentEntity;
import edu.hust.document.service.IDocumentService;

@CrossOrigin
@RestController
@RequestMapping("/api/document")
public class DocumentAPI {

	@Autowired
	private IDocumentService documentService;
	
	@PostMapping
	public DocumentEntity createDocument(@RequestBody DocumentDTO documentDTO) {
		return documentService.createDocument(documentDTO);
	}
	
	@PutMapping(value = "/{documentId}")
	public DocumentEntity updateDocument(
			@RequestBody DocumentDTO documentDTO, 
			@PathVariable("documentId") Long documentId) {
		return documentService.updateDocument(documentDTO, documentId);
	}
	
	@GetMapping(params = {"documentId"})
	public DocumentEntity getDocumentById(@RequestParam Long documentId) {
		return documentService.findDocumentById(documentId);
	}
}
