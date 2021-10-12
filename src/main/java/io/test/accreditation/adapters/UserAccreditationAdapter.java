package io.test.accreditation.adapters;

import io.test.accreditation.controllers.dto.UserAccreditationRequestDTO;
import io.test.accreditation.controllers.dto.UserAccreditationResponseDTO;
import reactor.core.publisher.Mono;

public interface UserAccreditationAdapter {
    Mono<UserAccreditationResponseDTO> processAccreditationRequest(UserAccreditationRequestDTO request);
}
