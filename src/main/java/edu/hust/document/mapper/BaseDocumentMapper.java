package edu.hust.document.mapper;

import edu.hust.document.dto.BaseDocumentDTO;
import edu.hust.document.dto.DepartmentDTO;
import edu.hust.document.dto.UserDTO;
import edu.hust.document.entity.BaseDocumentEntity;
import edu.hust.document.entity.DepartmentEntity;
import org.modelmapper.ModelMapper;

public class BaseDocumentMapper {
    public static BaseDocumentDTO baseDocumentEntityToBasicDTO(BaseDocumentEntity baseDocumentEntity) {
        BaseDocumentDTO baseDocumentDTO = new BaseDocumentDTO();
        baseDocumentDTO.setId(baseDocumentEntity.getId());
        baseDocumentDTO.setAgencyCode(baseDocumentEntity.getAgencyCode());
        baseDocumentDTO.setCode(baseDocumentEntity.getCode());
        baseDocumentDTO.setName(baseDocumentEntity.getName());
        baseDocumentDTO.setContent(baseDocumentEntity.getContent());
        baseDocumentDTO.setCreatedBy(baseDocumentEntity.getCreatedBy());

        return baseDocumentDTO;
    }

    public static BaseDocumentDTO baseDocumentEntityToDetailDTO(BaseDocumentEntity baseDocumentEntity) {
        BaseDocumentDTO baseDocumentDTO = baseDocumentEntityToBasicDTO(baseDocumentEntity);

        return baseDocumentDTO;
    }
}
