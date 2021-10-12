package io.test.accreditation.models;

public class AccreditationResult {
    private Boolean success;
    private Boolean accredited;

    public AccreditationResult(Boolean success, Boolean accredited) {
        this.success = success;
        this.accredited = accredited;
    }

    public Boolean isSuccess() {
        return success;
    }

    public Boolean isAccredited() {
        return accredited;
    }

    @Override
    public String toString() {
        return "UserAccreditationResult{" +
                "success=" + success +
                ", accredited=" + accredited +
                '}';
    }
}
