package edu.hust.document.service.impl;

import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.entity.*;
import edu.hust.document.form.DocumentForm;
import edu.hust.document.repository.BaseDocumentRepository;
import edu.hust.document.repository.CategoryRepository;
import edu.hust.document.repository.DocumentRepository;
import edu.hust.document.repository.UserRepository;
import edu.hust.document.service.IOutGoingDocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutGoingDocumentService implements IOutGoingDocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private BaseDocumentRepository baseDocumentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DocumentDTO> findAll() {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryName("Văn bản đi");
        return  setListDTO(documentEntityList);
    }

    @Override
    public List<DocumentDTO> findLikeByName(String name) {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryNameAndDocumentName("Văn bản đi", name);
        return  setListDTO(documentEntityList);
    }

    @Override
    public DocumentDTO findById(Long id) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(id);
        if (documentEntity == null){
            return null;
        }
        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        return  documentDTO;
    }

    @Override
    public DocumentDTO insert(DocumentForm documentForm) {
        DocumentEntity documentEntity = new DocumentEntity();
        BaseDocumentEntity baseDocumentEntity = new BaseDocumentEntity();
        setDocumentFormForEntityToInsert(documentForm, documentEntity, baseDocumentEntity);

        DocumentEntity documentEntity1;
        BaseDocumentEntity baseDocumentEntity1;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
            documentEntity.setId(baseDocumentEntity1.getId());
            documentEntity1 = documentRepository.save(documentEntity);
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = modelMapper.map(documentEntity1, DocumentDTO.class);
        documentDTO.setStatus(baseDocumentEntity1.getStatus());

        return documentDTO;
    }

    @Override
    public DocumentDTO update(DocumentForm documentForm) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(documentForm.getId());
        if (documentEntity == null) return null;
        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        setDocumentFormForEntityToUpdate(documentForm, baseDocumentEntity);

        BaseDocumentEntity baseDocumentEntity1 = null;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);

        return documentDTO;
    }

    @Override
    public DocumentDTO assignNumbers(DocumentForm documentForm) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(documentForm.getId());
        if (documentEntity == null) return null;
        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        setDocumentFormForEntityToSetAssignNumbers(documentForm, baseDocumentEntity);

        BaseDocumentEntity baseDocumentEntity1 = null;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);

        return documentDTO;
    }

    @Override
    public DocumentDTO accept(Long id) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(id);
        if (documentEntity == null) return null;
        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        baseDocumentEntity.setStatus("Hoàn thành");

        BaseDocumentEntity baseDocumentEntity1 = null;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);

        return documentDTO;
    }

    @Override
    public DocumentDTO refuse(Long id) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(id);
        if (documentEntity == null) return null;
        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        baseDocumentEntity.setStatus("Chờ chỉnh sửa");

        BaseDocumentEntity baseDocumentEntity1 = null;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);

        return documentDTO;
    }

    private void setDocumentFormForEntityToInsert(DocumentForm documentForm, DocumentEntity documentEntity,
                                     BaseDocumentEntity baseDocumentEntity){

        baseDocumentEntity.setCreatedBy(documentForm.getCreated_by());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setCreatedDate(date);

        documentEntity.setDeadline(documentForm.getDeadline());
        documentEntity.setSecurityLevel(documentForm.getSecurityLevel());
        documentEntity.setUrgencyLevel(documentForm.getUrgencyLevel());

        baseDocumentEntity.setStatus("Chờ cấp số");
        baseDocumentEntity.setName(documentForm.getName());
        baseDocumentEntity.setContent(documentForm.getContent());
        baseDocumentEntity.setNumberOfPage(documentForm.getNumberOfPage());
        baseDocumentEntity.setIssuanceTime(documentForm.getIssuanceTime());
        baseDocumentEntity.setFile(documentForm.getFile());

        UserEntity user = userRepository.findUserEntityById(documentForm.getAssigneeId());

        CategoryEntity categoryEntity =
                categoryRepository.findCategoryEntityById(documentForm.getCategoryId());

        baseDocumentEntity.setAssignee(user);
        baseDocumentEntity.setCategory(categoryEntity);
    }

    private void setDocumentFormForEntityToUpdate(DocumentForm documentForm,
                                          BaseDocumentEntity baseDocumentEntity){

        baseDocumentEntity.setModifedBy(documentForm.getModifed_by());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setModifedDate(date);

        baseDocumentEntity.setStatus("Đã chỉnh sửa");
        baseDocumentEntity.setFile(documentForm.getFile());
    }

    private void setDocumentFormForEntityToSetAssignNumbers(DocumentForm documentForm,
                                                  BaseDocumentEntity baseDocumentEntity){

        baseDocumentEntity.setModifedBy(documentForm.getModifed_by());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setModifedDate(date);

        baseDocumentEntity.setStatus("Đã cấp số");
        baseDocumentEntity.setAgencyCode(documentForm.getAgencyCode());
        baseDocumentEntity.setNumber(documentForm.getNumber());
        baseDocumentEntity.setSymbol(documentForm.getSymbol());
    }

    private List<DocumentDTO> setListDTO(List<DocumentEntity> documentEntityList){
        List<DocumentDTO> documentDTOList = new ArrayList<>();

        for (DocumentEntity documentEntity: documentEntityList) {
            documentDTOList.add(setDTO(documentEntity));
        }
        return  documentDTOList;
    }

    private DocumentDTO setDTO(DocumentEntity documentEntity) {
        DocumentDTO documentDTO = modelMapper.map(documentEntity, DocumentDTO.class);
        documentDTO.setName(documentEntity.getBaseDocumentEntity().getName());
        documentDTO.setType(documentEntity.getBaseDocumentEntity().getCategory().getType());
        documentDTO.setCreatedDate(documentEntity.getBaseDocumentEntity().getCreatedDate());
        documentDTO.setStatus(documentEntity.getBaseDocumentEntity().getStatus());

        return documentDTO;
    }

    private void setDTODetails(DocumentEntity documentEntity, DocumentDTO documentDTO) {
        documentDTO.setDeadline(documentEntity.getDeadline());
        documentDTO.setAgencyCode(documentEntity.getBaseDocumentEntity().getAgencyCode());
        documentDTO.setCreatedBy(documentEntity.getBaseDocumentEntity().getCreatedBy());
        documentDTO.setModifedDate(documentEntity.getBaseDocumentEntity().getModifedDate());
        documentDTO.setModifedBy(documentEntity.getBaseDocumentEntity().getModifedBy());
        documentDTO.setContent(documentEntity.getBaseDocumentEntity().getContent());
        documentDTO.setNumber(documentEntity.getBaseDocumentEntity().getNumber());
        documentDTO.setAgencyCode(documentEntity.getBaseDocumentEntity().getAgencyCode());
        documentDTO.setSymbol(documentEntity.getBaseDocumentEntity().getSymbol());
        documentDTO.setIssuanceTime(documentEntity.getBaseDocumentEntity().getIssuanceTime());
        documentDTO.setCategoryCode(documentEntity.getBaseDocumentEntity().getCategory().getCode());

        UserEntity user = documentEntity.getBaseDocumentEntity().getAssignee();
        if (user != null) {
            documentDTO.setAssigneeId(user.getId());
        }

        documentDTO.setNumberOfPage(documentEntity.getBaseDocumentEntity().getNumberOfPage());
        documentDTO.setFile(documentEntity.getBaseDocumentEntity().getFile());
    }
}
