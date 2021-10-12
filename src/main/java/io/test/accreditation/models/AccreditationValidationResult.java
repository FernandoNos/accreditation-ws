package io.test.accreditation.models;

public class AccreditationValidationResult {
    private Boolean success;

    public AccreditationValidationResult(Boolean success){
        this.success = success;
    }

    public Boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "AccreditationValidationResult{" +
                "success=" + success +
                '}';
    }
}
