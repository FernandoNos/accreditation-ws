package io.test.accreditation.usecases.accreditation.byincome.handler;

import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.AccreditationResult;
import reactor.core.publisher.Mono;

public interface AccreditationByIncomeRequestHandler {
    Mono<AccreditationResult> handleAccreditationRequest(UserAccreditationRequest userAccreditationRequest);
}
