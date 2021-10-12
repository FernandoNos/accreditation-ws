package io.test.accreditation.controllers.dto;

public class UserAccreditationResponseDTO {

    private Boolean success;
    private Boolean accredited;

    public UserAccreditationResponseDTO(Boolean success, Boolean accredited) {
        this.success = success;
        this.accredited = accredited;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getAccredited() {
        return accredited;
    }

    public void setAccredited(Boolean accredited) {
        this.accredited = accredited;
    }
}
