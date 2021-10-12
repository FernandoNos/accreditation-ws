package io.test.accreditation.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.test.accreditation.enums.AccreditationType;

import java.util.List;

public class UserAccreditationRequestPayload {

    private AccreditationType accreditationType;
    private List<UserAccreditationRequestPayloadDocument> documents;

    public UserAccreditationRequestPayload(AccreditationType accreditationType, List<UserAccreditationRequestPayloadDocument> documents) {
        this.accreditationType = accreditationType;
        this.documents = documents;
    }

    public AccreditationType getAccreditationType() {
        return accreditationType;
    }
    public List<UserAccreditationRequestPayloadDocument> getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return "UserAccreditationRequestPayload{" +
                "accreditationType=" + accreditationType +
                ", documents=" + documents +
                '}';
    }
}
