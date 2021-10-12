package io.test.accreditation.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserAccreditationRequestDTO {

    @JsonProperty("user_id")
    @NotNull
    private String userId;

    @Valid
    @NotNull
    private UserAccreditationRequestPayloadDTO payload;

    public UserAccreditationRequestDTO(){}

    public UserAccreditationRequestDTO(String userId, UserAccreditationRequestPayloadDTO payload) {
        this.userId = userId;
        this.payload = payload;
    }

    public String getUserId() {
        return userId;
    }

    public UserAccreditationRequestPayloadDTO getPayload() {
        return payload;
    }
}
