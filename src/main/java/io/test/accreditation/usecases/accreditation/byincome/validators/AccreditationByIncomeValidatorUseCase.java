package io.test.accreditation.usecases.accreditation.byincome.validators;

import io.test.accreditation.models.AccreditationValidationResult;
import io.test.accreditation.models.UserAccreditationRequest;
import reactor.core.publisher.Mono;

public interface AccreditationByIncomeValidatorUseCase {

    Mono<Boolean> validate(UserAccreditationRequest userAccreditationRequest);
}
