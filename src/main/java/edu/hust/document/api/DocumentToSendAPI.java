package edu.hust.document.api;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.service.IDocumentToSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/documenttosend")
public class DocumentToSendAPI {

    @Autowired
    private IDocumentToSendService documentToSendService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<DocumentDTO> documentDTOList = documentToSendService.findAll();
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findByNameLike(@RequestParam(name = "name") String name) {
        List<DocumentDTO> documentDTOList = documentToSendService.findLikeByName(name);
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        DocumentDTO documentDTO = documentToSendService.findById(id);
        return ResponseEntity.ok(documentDTO);
    }
}
