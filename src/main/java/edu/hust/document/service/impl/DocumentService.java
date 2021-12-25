package edu.hust.document.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hust.document.dto.DocumentDTO;
import edu.hust.document.entity.BaseDocumentEntity;
import edu.hust.document.entity.CategoryEntity;
import edu.hust.document.entity.DocumentEntity;
import edu.hust.document.exception.ResourceNotFoundException;
import edu.hust.document.mapper.BaseDocumentMapper;
import edu.hust.document.mapper.DocumentMapper;
import edu.hust.document.repository.BaseDocumentRepository;
import edu.hust.document.repository.CategoryRepository;
import edu.hust.document.repository.DocumentRepository;
import edu.hust.document.service.IDocumentService;

@Service
public class DocumentService implements IDocumentService {
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private BaseDocumentRepository baseDocumentRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BaseDocumentMapper baseDocumentMapper;
	
	@Autowired
	private DocumentMapper documentMapper;

	@Override
	public DocumentEntity createDocument(DocumentDTO documentDTO) {
		DocumentEntity documentEntity = documentMapper.toEntity(documentDTO);
		BaseDocumentEntity baseDocumentEntity = baseDocumentMapper.toEntity(documentDTO);
		CategoryEntity categoryEntity = categoryRepository.findCategoryByCode(documentDTO.getCategoryCode());
		baseDocumentEntity.setCategory(categoryEntity);
		
		baseDocumentEntity = baseDocumentRepository.save(baseDocumentEntity);
		documentEntity.setBaseDocumentEntity(baseDocumentEntity);
		return documentRepository.save(documentEntity);
	}

	@Override
	public DocumentEntity updateDocument(DocumentDTO documentDTO, Long documentId) {
		CategoryEntity categoryEntity = categoryRepository.findCategoryByCode(documentDTO.getCategoryCode());
		BaseDocumentEntity baseDocumentEntity = baseDocumentRepository.findById(documentId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(documentId, BaseDocumentEntity.class);
				});
		baseDocumentEntity = baseDocumentMapper.toEntity(baseDocumentEntity, documentDTO);
		baseDocumentEntity.setCategory(categoryEntity);
		
		DocumentEntity documentEntity = documentRepository.findById(documentId)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(documentId, DocumentEntity.class);
				});
		documentEntity = documentMapper.toEntity(documentEntity, documentDTO);
		documentEntity.setBaseDocumentEntity(baseDocumentEntity);
		
		return documentRepository.save(documentEntity);	
	}

	@Override
	public DocumentEntity findDocumentById(Long id) {
		return documentRepository.findById(id)
				.orElseThrow(() -> {
					return new ResourceNotFoundException(id, DocumentEntity.class);
				});
	}

}
