package io.test.accreditation.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserAccreditationRequestPayloadDocumentDTO {

    @NotNull
    @JsonProperty("mime_type")
    private String mimeType;
    @NotBlank
    private String name;
    @NotNull
    private String content;

    public UserAccreditationRequestPayloadDocumentDTO(){}
    public UserAccreditationRequestPayloadDocumentDTO(String mimeType, String name, String content) {
        this.mimeType = mimeType;
        this.name = name;
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
