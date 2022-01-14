package edu.hust.document.api;

import java.util.List;

import edu.hust.document.dto.HandlingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.service.IHandlingService;

@CrossOrigin
@RestController
@RequestMapping("/api/handling")
public class HandlingAPI {
	
	@Autowired
	private IHandlingService handlingService;
	
	@GetMapping(params = {"documentId"})
	public List<HandlingEntity> getHandlingHistory(@RequestParam Long documentId) {
		return handlingService.findByDocumentId(documentId);
	}

}
