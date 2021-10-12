package io.test.accreditation.models;

public class UserAccreditationRequest {

    private String userId;
    private UserAccreditationRequestPayload payload;

    public UserAccreditationRequest(String userId, UserAccreditationRequestPayload payload) {
        this.userId = userId;
        this.payload = payload;
    }

    public String getUserId() {
        return userId;
    }
    public UserAccreditationRequestPayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "UserAccreditationRequest{" +
                "userId='" + userId + '\'' +
                ", payload=" + payload +
                '}';
    }
}
