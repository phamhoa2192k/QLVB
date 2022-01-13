package edu.hust.document.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlingForm {
    private String note;
    private Long baseDocumentId;
    private Long userId;
}
