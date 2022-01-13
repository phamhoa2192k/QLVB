package edu.hust.document.api;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.form.DocumentForm;
import edu.hust.document.form.HandlingForm;
import edu.hust.document.service.IOutGoingDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/outgoingdocument")
public class OutGoingDocumentAPI {
    @Autowired
    private IOutGoingDocumentService outGoingDocumentService;

    @GetMapping(value = "/findAll")
    public ResponseEntity<Object> findAll() {
        List<DocumentDTO> documentDTOList = outGoingDocumentService.findAll();
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/findAll/clericalassistant")
    public ResponseEntity<Object> findAllForClericalAssistant() {
        List<DocumentDTO> documentDTOList = outGoingDocumentService.findAllForClericalAssistant();
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/findAll/employee/{id}")
    public ResponseEntity<Object> findAllForEmployee(@PathVariable Long id) {
        List<DocumentDTO> documentDTOList = outGoingDocumentService.findAllForEmployee(id);
        return ResponseEntity.ok(documentDTOList);
    }

    @GetMapping(value = "/findAll/leader")
    public ResponseEntity<Object> findAllForLeader() {
        List<DocumentDTO> documentDTOList = outGoingDocumentService.findAllForLeader();
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

    @PutMapping(value = "/accept")
    public ResponseEntity<Object> accept(@RequestBody HandlingForm handlingForm) {
        DocumentDTO documentDTO = outGoingDocumentService.accept(handlingForm);
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping(value = "/refuse")
    public ResponseEntity<Object> refuse(@RequestBody HandlingForm handlingForm) {
        DocumentDTO documentDTO = outGoingDocumentService.refuse(handlingForm);
        return ResponseEntity.ok(documentDTO);
    }
}
