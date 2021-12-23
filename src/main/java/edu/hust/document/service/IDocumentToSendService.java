package edu.hust.document.service;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.form.DocumentForm;

import java.util.List;

public interface IDocumentToSendService {
    List<DocumentDTO> findAll();

    List<DocumentDTO> findLikeByName(String name);

    DocumentDTO findById(Long id);

    DocumentDTO insert(DocumentForm documentForm);
}
