package edu.hust.document.service.impl;

import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.*;
import edu.hust.document.form.DocumentForm;
import edu.hust.document.repository.BaseDocumentRepository;
import edu.hust.document.repository.CategoryRepository;
import edu.hust.document.repository.DocumentRepository;
import edu.hust.document.service.IDocumentToSendService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DocumentToSendService implements IDocumentToSendService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private BaseDocumentRepository baseDocumentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DocumentDTO> findAll() {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryName("Văn bản đi");
        List<DocumentDTO> documentDTOList = new ArrayList<>();

        for (DocumentEntity documentEntity: documentEntityList) {
            DocumentDTO documentDTO = modelMapper.map(documentEntity, DocumentDTO.class);
            BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
            BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity, BaseDocumentDTO.class);
            baseDocumentDTO.setType(baseDocumentEntity.getCategory().getType());
            documentDTO.setBaseDocumentDTO(baseDocumentDTO);
            documentDTOList.add(documentDTO);
        }
        return  documentDTOList;
    }

    @Override
    public List<DocumentDTO> findLikeByName(String name) {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryNameAndDocumentName("Văn bản đi", name);
        List<DocumentDTO> documentDTOList = new ArrayList<>();

        for (DocumentEntity documentEntity: documentEntityList) {
            DocumentDTO documentDTO = modelMapper.map(documentEntity, DocumentDTO.class);
            BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
            BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity, BaseDocumentDTO.class);
            baseDocumentDTO.setType(baseDocumentEntity.getCategory().getType());
            documentDTO.setBaseDocumentDTO(baseDocumentDTO);
            documentDTOList.add(documentDTO);
        }
        return  documentDTOList;
    }

    @Override
    public DocumentDTO findById(Long id) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(id);
        if (documentEntity == null){
            return null;
        }

        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        DocumentDTO documentDTO = modelMapper.map(documentEntity, DocumentDTO.class);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity, BaseDocumentDTO.class);

        Set<HandlingEntity> handlingEntitySet = baseDocumentEntity.getHandlings();
        List<HandlingDTO> handlingDTOList = new ArrayList<>();
        for (HandlingEntity handlingEntity:
                handlingEntitySet) {
            HandlingDTO handlingDTO = modelMapper.map(handlingEntity, HandlingDTO.class);
            UserDTO userDTO = modelMapper.map(handlingEntity.getUser(), UserDTO.class);
            userDTO.setDepartmentCode(handlingEntity.getUser().getDepartment().getCode());
            handlingDTO.setUserDTO(userDTO);
            handlingDTOList.add(handlingDTO);
        }
        baseDocumentDTO.setType(documentEntity.getBaseDocumentEntity().getCategory().getType());
        baseDocumentDTO.setHandlingDTO(handlingDTOList);

        documentDTO.setBaseDocumentDTO(baseDocumentDTO);

        return  documentDTO;
    }

    @Override
    public DocumentDTO insert(DocumentForm documentForm) {
        DocumentEntity documentEntity = new DocumentEntity();
        BaseDocumentEntity baseDocumentEntity = new BaseDocumentEntity();
        setDocumentFormForEntity(documentForm, documentEntity, baseDocumentEntity);

        baseDocumentEntity.setCreatedBy(documentForm.getCreated_by());
        baseDocumentEntity.setStatus("Vừa tạo");
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setCreatedDate(date);

        DocumentEntity documentEntity1;
        BaseDocumentEntity baseDocumentEntity1;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
            documentEntity.setId(baseDocumentEntity1.getId());
            documentEntity1 = documentRepository.save(documentEntity);
        }catch (Exception e) {
            return null;
        }

        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);
        DocumentDTO documentDTO = modelMapper.map(documentEntity1, DocumentDTO.class);
        documentDTO.setBaseDocumentDTO(baseDocumentDTO);

        return documentDTO;
    }

    void setDocumentFormForEntity(DocumentForm documentForm, DocumentEntity documentEntity,
                                     BaseDocumentEntity baseDocumentEntity){

        documentEntity.setDeadline(documentForm.getDeadline());
        documentEntity.setAttachedDocument(documentForm.getAttachedDocument());
        documentEntity.setSecurityLevel(documentForm.getSecurityLevel());
        documentEntity.setUrgencyLevel(documentForm.getUrgencyLevel());

        baseDocumentEntity.setCode(documentForm.getCode());
        baseDocumentEntity.setName(documentForm.getName());
        baseDocumentEntity.setContent(documentForm.getContent());
        baseDocumentEntity.setAgencyCode(documentForm.getAgencyCode());
        baseDocumentEntity.setNumber(documentForm.getNumber());
        baseDocumentEntity.setSignerName(documentForm.getSignerName());
        baseDocumentEntity.setSignerPosition(documentForm.getSignerPosition());
        baseDocumentEntity.setIssuanceTime(documentForm.getIssuanceTime());
        baseDocumentEntity.setForwardTime(documentForm.getForwardTime());
        baseDocumentEntity.setOtherInfo(documentForm.getOtherInfo());

        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(documentForm.getCategory_id());

        baseDocumentEntity.setCategory(categoryEntity);
    }
}