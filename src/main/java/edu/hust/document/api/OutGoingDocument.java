package edu.hust.document.api;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.form.DocumentForm;
import edu.hust.document.service.IOutGoingDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/outgoingdocument")
public class OutGoingDocument {
    @Autowired
    private IOutGoingDocumentService outGoingDocumentService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<DocumentDTO> documentDTOList = outGoingDocumentService.findAll();
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findByNameLike(@RequestParam(name = "name") String name) {
        List<DocumentDTO> documentDTOList = outGoingDocumentService.findLikeByName(name);
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        DocumentDTO documentDTO = outGoingDocumentService.findById(id);
        return ResponseEntity.ok(documentDTO);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> insert(@RequestBody DocumentForm documentForm) {
        DocumentDTO documentDTO = outGoingDocumentService.insert(documentForm);
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping(value = "/assignnumber")
    public ResponseEntity<Object> assignnumber(@RequestBody DocumentForm documentForm) {
        DocumentDTO documentDTO = outGoingDocumentService.assignNumbers(documentForm);
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody DocumentForm documentForm) {
        DocumentDTO documentDTO = outGoingDocumentService.update(documentForm);
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping(value = "/accept/{id}")
    public ResponseEntity<Object> accept(@PathVariable Long id) {
        DocumentDTO documentDTO = outGoingDocumentService.accept(id);
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping(value = "/refuse/{id}")
    public ResponseEntity<Object> refuse(@PathVariable Long id) {
        DocumentDTO documentDTO = outGoingDocumentService.refuse(id);
        return ResponseEntity.ok(documentDTO);
    }
}
