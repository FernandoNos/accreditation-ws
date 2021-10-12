package io.test.accreditation.usecases.accreditation.byincome.handler;

import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.AccreditationResult;
import io.test.accreditation.usecases.accreditation.byincome.validators.AccreditationByIncomeValidatorUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Component
public class AccreditationByIncomeRequestHandlerImpl implements AccreditationByIncomeRequestHandler {

    //TODO: Replace with proper accreditation logic
    private final Random randomBoolean;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
    private Flux<AccreditationByIncomeValidatorUseCase> accreditationValidators;

    @Autowired
    public void setValidators(List<AccreditationByIncomeValidatorUseCase> accreditationValidators){
        this.accreditationValidators = Flux.fromIterable(accreditationValidators);
    }

    public AccreditationByIncomeRequestHandlerImpl(){
        this.randomBoolean = new Random();
    }

    @Override
    public Mono<AccreditationResult> handleAccreditationRequest(UserAccreditationRequest userAccreditationRequest) {
        return this.executeValidations(userAccreditationRequest)
                .filter(result -> result)
                .doOnNext(it -> LOGGER.info("Validation result {}", it))
                .flatMap(it -> this.submitAccreditationRequest(userAccreditationRequest))
                .switchIfEmpty(Mono.defer(()->{
                    LOGGER.error("Invalid request received {}", userAccreditationRequest);
                    return Mono.just(new AccreditationResult(false, false));
                }));
    }

    private Mono<Boolean> executeValidations(UserAccreditationRequest userAccreditationRequest){
        return accreditationValidators
                .flatMap(it -> it.validate(userAccreditationRequest))
                .reduce((resultA, resultB) -> resultA && resultB);
    }

    public Mono<AccreditationResult> submitAccreditationRequest(UserAccreditationRequest userAccreditationRequest) {
        return Mono.just(this.randomBoolean.nextBoolean())
                .doOnNext(it -> LOGGER.info("Accreditation submission result {}", it))
                .map(it -> new AccreditationResult(true,it))
                .doOnError(error -> LOGGER.error("Accreditation submission finished with error {}", error.getMessage()));
    }
}
