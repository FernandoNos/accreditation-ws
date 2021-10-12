package io.test.accreditation.services;

import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.AccreditationResult;
import reactor.core.publisher.Mono;

public interface UserAccreditationService {
    Mono<AccreditationResult> processUserAccreditation(UserAccreditationRequest userAccreditationRequest);
}
