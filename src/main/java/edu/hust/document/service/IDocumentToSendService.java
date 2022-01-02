package edu.hust.document.service;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.form.DocumentForm;

import java.util.List;

public interface IDocumentToSendService {
    List<DocumentDTO> findAll(String categoryName);

    List<DocumentDTO> findLikeByName(String name, String categoryName);

    DocumentDTO findById(Long id);

    DocumentDTO insert(DocumentForm documentForm, String categoryName);

    DocumentDTO update(DocumentForm documentForm, String categoryName);
}
