package edu.hust.document.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hust.document.api.input.FileUpload;
import edu.hust.document.configs.Configs;
import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.dto.HandlingDTO;
import edu.hust.document.entity.BaseDocumentEntity;
import edu.hust.document.entity.CategoryEntity;
import edu.hust.document.entity.DocumentEntity;
import edu.hust.document.entity.HandlingEntity;
import edu.hust.document.entity.UserEntity;
import edu.hust.document.exception.ResourceNotFoundException;
import edu.hust.document.mapper.BaseDocumentMapper;
import edu.hust.document.mapper.DocumentMapper;
import edu.hust.document.mapper.HandlingMapper;
import edu.hust.document.repository.BaseDocumentRepository;
import edu.hust.document.repository.CategoryRepository;
import edu.hust.document.repository.DocumentRepository;
import edu.hust.document.repository.HandlingRepository;
import edu.hust.document.repository.UserRepository;
import edu.hust.document.service.IncomingDocumentInterface;

@Service
public class IncomingDocumentService implements IncomingDocumentInterface {
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private BaseDocumentRepository baseDocumentRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BaseDocumentMapper baseDocumentMapper;
	
	@Autowired
	private HandlingMapper handlingMapper;
	
	@Autowired
	private DocumentMapper documentMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HandlingRepository handlingRepository;

	@Override
	public DocumentEntity createDocument(DocumentDTO documentDTO) {
		DocumentEntity documentEntity = documentMapper.toEntity(documentDTO);
		BaseDocumentEntity baseDocumentEntity = baseDocumentMapper.toEntity(documentDTO);
		
		if (documentDTO.getAssigneeId() != null) {
			UserEntity assignee = userRepository.findById(documentDTO.getAssigneeId())
					.orElseThrow(() -> {
						return new ResourceNotFoundException(documentDTO.getAssigneeId(), UserEntity.class);
					});
			baseDocumentEntity.setAssignee(assignee);
		}
		
		CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(documentDTO.getCategoryId());
		baseDocumentEntity.setCategory(categoryEntity);
		
		baseDocumentEntity = baseDocumentRepository.save(baseDocumentEntity);
		//documentEntity.setBaseDocumentEntity(baseDocumentEntity);
		documentEntity.setId(baseDocumentEntity.getId());
		
		return documentRepository.save(documentEntity);
	}

	@Override
	public DocumentEntity updateDocument(DocumentDTO documentDTO, Long documentId) {
		CategoryEntity categoryEntity = categoryRepository.findCategoryEntityById(documentDTO.getCategoryId());
		BaseDocumentEntity baseDocumentEntity = baseDocumentRepository.findById(documentId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(documentId, BaseDocumentEntity.class);
				});
		baseDocumentEntity = baseDocumentMapper.toEntity(baseDocumentEntity, documentDTO);
		
		if (documentDTO.getAssigneeId() != null) {
			UserEntity assignee = userRepository.findById(documentDTO.getAssigneeId())
					.orElseThrow(() -> {
						return new ResourceNotFoundException(documentDTO.getAssigneeId(), UserEntity.class);
					});
			baseDocumentEntity.setAssignee(assignee);
		}
		
		baseDocumentEntity.setCategory(categoryEntity);
		
		DocumentEntity documentEntity = documentRepository.findById(documentId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(documentId, DocumentEntity.class);
				});
		documentEntity = documentMapper.toEntity(documentEntity, documentDTO);
		documentEntity.setBaseDocumentEntity(baseDocumentEntity);
		documentEntity.setId(baseDocumentEntity.getId());
		
		return documentRepository.save(documentEntity);	
	}

	@Override
	public DocumentEntity findDocumentById(Long id) {
		return documentRepository.findById(id)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(id, DocumentEntity.class);
				});
	}

	@Override
	public List<DocumentEntity> findDocumentByAssigneeId(Long assigneeId) {
		return documentRepository.findDocumentEntityByAssignee(assigneeId);
	}

	@Override
	public void assignDocumentForUser(Long documentId, Long userId) {
		BaseDocumentEntity baseDocumentEntity = baseDocumentRepository.findById(documentId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(documentId, BaseDocumentEntity.class);
				});
		UserEntity assignee = userRepository.findById(userId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(userId, UserEntity.class);
				});
		baseDocumentEntity.setAssignee(assignee);
		baseDocumentEntity = baseDocumentRepository.save(baseDocumentEntity);
	}

	@Override
	public void handleDocument(HandlingDTO handlingDTO) {
		BaseDocumentEntity baseDocumentEntity = baseDocumentRepository.findById(handlingDTO.getDocumentId())
				.orElseThrow(() -> {
					return new ResourceNotFoundException(handlingDTO.getDocumentId(), BaseDocumentEntity.class);
				});
		
		UserEntity handler = userRepository.findById(handlingDTO.getHandlingUserId())
				.orElseThrow(() -> {
					return new ResourceNotFoundException(handlingDTO.getHandlingUserId(), UserEntity.class);
				});
		
		HandlingEntity handlingEntity = handlingMapper.toEntity(handlingDTO);
		handlingEntity.setBaseDocument(baseDocumentEntity);
		handlingEntity.setUser(handler);
		handlingRepository.save(handlingEntity);
		
		if (handlingDTO.getAction() == Configs.ACTION_APPROVE) {
			baseDocumentEntity.setStatus(Configs.STATUS_CLOSED);
		} else if (handlingDTO.getAction() == Configs.ACTION_CREATE) {
			baseDocumentEntity.setStatus(Configs.STATUS_INITIAL);
		} else if (handlingDTO.getAction() == Configs.ACTION_HANDLE) {
			baseDocumentEntity.setStatus(Configs.STATUS_HANDLED);
		} else if (handlingDTO.getAction() == Configs.ACTION_REFUSE) {
			baseDocumentEntity.setStatus(Configs.STATUS_WAIT_FOR_HANDLE_AGAIN);
		} else if (handlingDTO.getAction() == Configs.ACTION_ASSIGN) {
			baseDocumentEntity.setStatus(Configs.STATUS_WAIT_FOR_HANDLE);
		}
		
		baseDocumentRepository.save(baseDocumentEntity);
	}

	@Override
	public List<HandlingEntity> getAllHandlingsByDocumentId(Long documentId) {
		return handlingRepository.findAllByBaseDocumentId(documentId);
	}

	@Override
	public void uploadFile(FileUpload fileUpload) {
		BaseDocumentEntity baseDocumentEntity = baseDocumentRepository.findById(fileUpload.getDocumentId())
				.orElseThrow(() -> {
					return new ResourceNotFoundException(fileUpload.getDocumentId(), BaseDocumentEntity.class);
				});
		
		baseDocumentEntity.setFile(fileUpload.getFile());
		baseDocumentRepository.save(baseDocumentEntity);
	}
	
}
