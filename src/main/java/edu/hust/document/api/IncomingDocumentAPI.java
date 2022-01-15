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

import edu.hust.document.api.input.DepartmentAssignment;
import edu.hust.document.api.input.FileUpload;
import edu.hust.document.dto.DocumentDTO;
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

	@GetMapping("/all")
	public List<DocumentEntity> getAll() {
		return incomingDocumentService.findAll();
	}

	@GetMapping("/all/clericalassistant")
	public List<DocumentEntity> getDocumentsOfClericalAssistant() {
		return incomingDocumentService.getDocumentsOfClericalAssistant();
	}

	@GetMapping("/all/employee/{departmentId}")
	public List<DocumentEntity> getDocumentsOfEmployee(@PathVariable @Valid @NotNull @Min(1) Long departmentId) {
		return incomingDocumentService.getDocumentsOfEmployee(departmentId);
	}

	@GetMapping("/all/leader")
	public List<DocumentEntity> getDocumentsOfLeader() {
		return incomingDocumentService.getDocumentsOfLeader();
	}

	@PutMapping(value = "/handle/assign")
	public void assignDepartment(@RequestBody DepartmentAssignment departmentAssignment) {
		incomingDocumentService.assignDepartment(departmentAssignment);
	}

	@PutMapping(value = "/handle/approve")
	public void approve(Long documentId) {
		incomingDocumentService.approveDocument(documentId);
	}

	@PutMapping(value = "/handle/refuse")
	public void refuse(Long documentId) {
		incomingDocumentService.refuseDocument(documentId);
	}

	@PutMapping(value = "/handle/upload")
	public void uploadFile(@RequestBody FileUpload fileUpload) {
		incomingDocumentService.uploadFile(fileUpload);
	}

	@PutMapping(value = "/handle/finishHandling")
	public void finishHandling(Long documentId) {
		incomingDocumentService.finishHandling(documentId);
	}

}
