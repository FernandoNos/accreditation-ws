package io.test.accreditation.adapters;

import io.test.accreditation.controllers.dto.UserAccreditationRequestDTO;
import io.test.accreditation.controllers.dto.UserAccreditationResponseDTO;
import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.UserAccreditationRequestPayload;
import io.test.accreditation.models.UserAccreditationRequestPayloadDocument;
import io.test.accreditation.services.UserAccreditationService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class UserAccreditationAdapterImpl implements UserAccreditationAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserAccreditationService userAccreditationService;

    @Override
    public Mono<UserAccreditationResponseDTO> processAccreditationRequest(UserAccreditationRequestDTO request) {
        return Mono.just(request)
                .map(it -> new UserAccreditationRequest(request.getUserId(), new UserAccreditationRequestPayload(
                        request.getPayload().getAccreditationType(),
                        request.getPayload().getDocuments().stream()
                                .map(document -> new UserAccreditationRequestPayloadDocument(document.getMimeType(),
                                        document.getName(),
                                        document.getContent()))
                                .collect(Collectors.toList())
                )))
                .doOnNext(it -> LOGGER.info("Converted request {}", it))
                .flatMap(it -> this.userAccreditationService.processUserAccreditation(it))
                .map(it -> new UserAccreditationResponseDTO(it.isSuccess(), it.isAccredited()))
                .doOnSuccess(it -> LOGGER.info("Result converted to DTO {}", it))
                .doOnError(error -> LOGGER.error("Accreditation processes finished with error {}", error.getMessage()));
    }
}
