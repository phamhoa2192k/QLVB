package edu.hust.document.service.impl;

import edu.hust.document.configs.Configs;
import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.entity.*;
import edu.hust.document.form.DocumentForm;
import edu.hust.document.form.HandlingForm;
import edu.hust.document.repository.*;
import edu.hust.document.service.IOutGoingDocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private HandlingRepository handlingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DocumentDTO> findAll() {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryName(Configs.OUTGOING_DOCUMENT_TYPE);
        return  setListDTO(documentEntityList);
    }

    @Override
    public List<DocumentDTO> findAllForClericalAssistant() {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatus(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_WAIT_FOR_ASSIGN_NUMBER);
        return  setListDTO(documentEntityList);
    }

    @Override
    public List<DocumentDTO> findAllForEmployee(Long EmployeeId) {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatusAndUserId(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_WAIT_FOR_UPDATE, EmployeeId);

        List<DocumentEntity> documentEntityList1 =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatusAndUserId(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_WAIT_FOR_ASSIGN_NUMBER, EmployeeId);

        List<DocumentEntity> documentEntityList2 =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatusAndUserId(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_UPDATED, EmployeeId);

        documentEntityList.addAll(documentEntityList1);
        documentEntityList.addAll(documentEntityList2);
        return  setListDTO(documentEntityList);
    }

    @Override
    public List<DocumentDTO> findAllForLeader() {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatus(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_UPDATED);

        List<DocumentEntity> documentEntityList1 =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatus(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_ASSIGNED_NUMBER);

        List<DocumentEntity> documentEntityList2 =
                documentRepository.findDocumentEntityByCategaryNameAndBaseDocumentEntityStatus(Configs.OUTGOING_DOCUMENT_TYPE,
                        Configs.STATUS_CLOSED);

        documentEntityList.addAll(documentEntityList1);
        documentEntityList.addAll(documentEntityList2);

        return  setListDTO(documentEntityList);
    }

    @Override
    public List<DocumentDTO> findLikeByName(String name) {
        List<DocumentEntity> documentEntityList =
                documentRepository.findDocumentEntityByCategaryNameAndDocumentName(Configs.OUTGOING_DOCUMENT_TYPE, name);
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
            insertHanling(Configs.ACTION_CREATE, "", baseDocumentEntity1, baseDocumentEntity1.getAssignee());
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
            insertHanling(Configs.ACTION_UPDATE, "", baseDocumentEntity1, baseDocumentEntity1.getAssignee());
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
            UserEntity user = userRepository.findUserEntityById(documentForm.getAssigneeId());
            insertHanling(Configs.ACTION_ASSIGN_NUMBER, "", baseDocumentEntity1, user);
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);

        return documentDTO;
    }

    @Override
    public DocumentDTO accept(HandlingForm handlingForm) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(handlingForm.getBaseDocumentId());
        if (documentEntity == null) return null;
        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        baseDocumentEntity.setStatus(Configs.STATUS_CLOSED);

        BaseDocumentEntity baseDocumentEntity1 = null;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
            UserEntity user = userRepository.findUserEntityById(handlingForm.getUserId());
            insertHanling(Configs.ACTION_APPROVE, "", baseDocumentEntity1, baseDocumentEntity1.getAssignee());
        }catch (Exception e) {
            return null;
        }

        DocumentDTO documentDTO = setDTO(documentEntity);
        setDTODetails(documentEntity, documentDTO);
        BaseDocumentDTO baseDocumentDTO = modelMapper.map(baseDocumentEntity1, BaseDocumentDTO.class);

        return documentDTO;
    }

    @Override
    public DocumentDTO refuse(HandlingForm handlingForm) {
        DocumentEntity documentEntity = documentRepository.findDocumentEntityById(handlingForm.getBaseDocumentId());
        if (documentEntity == null) return null;
        BaseDocumentEntity baseDocumentEntity = documentEntity.getBaseDocumentEntity();
        baseDocumentEntity.setStatus(Configs.STATUS_WAIT_FOR_UPDATE);

        BaseDocumentEntity baseDocumentEntity1 = null;
        try {
            baseDocumentEntity1 = baseDocumentRepository.save(baseDocumentEntity);
            UserEntity user = userRepository.findUserEntityById(handlingForm.getUserId());
            insertHanling(Configs.ACTION_REFUSE, handlingForm.getNote(), baseDocumentEntity1, baseDocumentEntity1.getAssignee());
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

        baseDocumentEntity.setStatus(Configs.STATUS_WAIT_FOR_ASSIGN_NUMBER);
        baseDocumentEntity.setName(documentForm.getName());
        baseDocumentEntity.setAgencyCode(documentForm.getAgencyCode());
        baseDocumentEntity.setSymbol(documentForm.getSymbol());
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

        baseDocumentEntity.setStatus(Configs.STATUS_UPDATED);
        baseDocumentEntity.setFile(documentForm.getFile());
    }

    private void setDocumentFormForEntityToSetAssignNumbers(DocumentForm documentForm,
                                                  BaseDocumentEntity baseDocumentEntity){

        baseDocumentEntity.setModifedBy(documentForm.getModifed_by());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        baseDocumentEntity.setModifedDate(date);

        baseDocumentEntity.setStatus(Configs.STATUS_ASSIGNED_NUMBER);
        baseDocumentEntity.setNumber(documentForm.getNumber());
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

        UserEntity user = documentEntity.getBaseDocumentEntity().getAssignee();
        if (user != null) {
            documentDTO.setAssigneeId(user.getId());
        }

        documentDTO.setNumberOfPage(documentEntity.getBaseDocumentEntity().getNumberOfPage());
        documentDTO.setFile(documentEntity.getBaseDocumentEntity().getFile());
    }

    private void insertHanling(String action, String note, BaseDocumentEntity baseDocumentEntity, UserEntity assignee){
        HandlingEntity handlingEntity = new HandlingEntity();
        handlingEntity.setAction(action);
        handlingEntity.setNote(note);

        LocalDateTime now = LocalDateTime.now();
        handlingEntity.setTime(now);

        handlingEntity.setBaseDocument(baseDocumentEntity);
        handlingEntity.setUser(assignee);

        handlingRepository.save(handlingEntity);
    }
}
