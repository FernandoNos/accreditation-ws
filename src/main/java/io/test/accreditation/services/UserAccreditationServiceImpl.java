package io.test.accreditation.services;

import io.test.accreditation.enums.AccreditationType;
import io.test.accreditation.exceptions.InvalidAccreditationTypeException;
import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.AccreditationResult;
import io.test.accreditation.usecases.accreditation.byincome.handler.AccreditationByIncomeRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserAccreditationServiceImpl implements UserAccreditationService {

    @Autowired
    private AccreditationByIncomeRequestHandler accreditationByIncomeRequestHandler;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Mono<AccreditationResult> processUserAccreditation(UserAccreditationRequest userAccreditationRequest) {
        return Mono.just(userAccreditationRequest)
                .flatMap(request -> {

                    if(request.getPayload().getAccreditationType() == AccreditationType.BY_INCOME)
                        return accreditationByIncomeRequestHandler.handleAccreditationRequest(userAccreditationRequest);

                    LOGGER.error("An invalid accreditation request was received: {}", userAccreditationRequest);
                    return Mono.error(new InvalidAccreditationTypeException());
                })
                .doOnSuccess(result -> LOGGER.info("Accreditation result {}", result))
                .doOnError(error -> LOGGER.error("Error processing accreditation request {}", error.getMessage()));
    }
}
