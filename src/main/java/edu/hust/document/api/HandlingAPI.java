package edu.hust.document.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.service.IHandlingService;
import edu.hust.document.service.IncomingDocumentInterface;

@CrossOrigin
@RestController
@RequestMapping("/api/handling")
public class HandlingAPI {

	@Autowired
	private IHandlingService handlingService;

	@Autowired
	private IncomingDocumentInterface incomingDocumentService;

	@GetMapping(params = {"documentId"})
	public List<HandlingEntity> getHandlingHistory(@RequestParam Long documentId) {
		return handlingService.findByDocumentId(documentId);
	}

	@PostMapping(value = "/handle")
	public void handle(@RequestBody HandlingDTO handlingDTO) {
		incomingDocumentService.handleDocument(handlingDTO);
	}

}
