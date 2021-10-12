package io.test.accreditation.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.test.accreditation.enums.AccreditationType;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserAccreditationRequestPayloadDTO {

    @NotNull
    @JsonProperty("accreditation_type")
    private AccreditationType accreditationType;

    @Valid
    @NotNull
    private List<UserAccreditationRequestPayloadDocumentDTO> documents;

    public UserAccreditationRequestPayloadDTO(){}

    public UserAccreditationRequestPayloadDTO(AccreditationType accreditationType, List<UserAccreditationRequestPayloadDocumentDTO> documents) {
        this.accreditationType = accreditationType;
        this.documents = documents;
    }

    public AccreditationType getAccreditationType() {
        return accreditationType;
    }

    public List<UserAccreditationRequestPayloadDocumentDTO> getDocuments() {
        return documents;
    }
}
