package edu.hust.document.api;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.form.DocumentForm;
import edu.hust.document.service.IDocumentToSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/receiveddocument")
public class ReceivedDocumentAPI {
    @Autowired
    private IDocumentToSendService documentToSendService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<DocumentDTO> documentDTOList = documentToSendService.findAll( "Văn bản đến");
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findByNameLike(@RequestParam(name = "name") String name) {
        List<DocumentDTO> documentDTOList = documentToSendService.findLikeByName(name, "Văn bản đến");
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        DocumentDTO documentDTO = documentToSendService.findById(id);
        return ResponseEntity.ok(documentDTO);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> insert(@RequestBody DocumentForm documentForm) {
        DocumentDTO documentDTO = documentToSendService.insert(documentForm, "Văn bản đến");
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody DocumentForm documentForm) {
        DocumentDTO documentDTO = documentToSendService.update(documentForm, "Văn bản đến");
        return ResponseEntity.ok(documentDTO);
    }
}
