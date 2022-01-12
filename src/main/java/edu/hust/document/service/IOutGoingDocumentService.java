package edu.hust.document.service;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.form.DocumentForm;

import java.util.List;

public interface IOutGoingDocumentService {
    List<DocumentDTO> findAll();

    List<DocumentDTO> findAllForClericalAssistant();

    List<DocumentDTO> findAllForEmployee(Long EmployeeId);

    List<DocumentDTO> findAllForLeader();

    List<DocumentDTO> findLikeByName(String name);

    DocumentDTO findById(Long id);

    DocumentDTO insert(DocumentForm documentForm);

    DocumentDTO update(DocumentForm documentForm);

    DocumentDTO assignNumbers(DocumentForm documentForm);

    DocumentDTO accept(Long id);

    DocumentDTO refuse(Long id);
}
