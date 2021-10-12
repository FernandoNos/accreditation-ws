package io.test.accreditation.usecases.accreditation.byincome.validators;

import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.UserAccreditationRequestPayloadDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.regex.Pattern;


@Component
public class AccreditationByIncomeValidatorUseCaseImpl implements AccreditationByIncomeValidatorUseCase {

    @Value("${accreditation.byincome.file.name.structure}")
    private String FILE_NAME_PATTERN;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public Mono<Boolean> validate(UserAccreditationRequest userAccreditationRequest) {
        return Mono.just(userAccreditationRequest)
                .doOnNext (it -> LOGGER.info("Analzying accreditation by income : {}", it))
                .flatMap (this::validateDocuments)
                .doOnNext(it -> LOGGER.info("Accreditation by income finished: {}", it))
                .doOnError(it -> LOGGER.error("Accreditation by income finished with error: {}", it.getMessage()));
    }
    private Mono<Boolean> validateDocuments(UserAccreditationRequest userAccreditationRequest){
        return
                Flux.merge(
                                this.checkNumberOfDocuments(userAccreditationRequest),
                                this.checkDocumentName(userAccreditationRequest),
                                this.checkDocumentDates(userAccreditationRequest)
                )
                .reduce(Boolean::logicalAnd)
                .doOnSuccess(it -> LOGGER.info("Documents validation finished with {} for {}",it, userAccreditationRequest))
                .doOnError(error -> LOGGER.error("Documents validation finished with error {}", error.getMessage()));
    }

    private Mono<Boolean> checkDocumentDates(UserAccreditationRequest userAccreditationRequest){
        //TODO: Validates the document content, to make sure that they are the last two years tax returns1212
        return Flux.fromIterable(userAccreditationRequest.getPayload().getDocuments())
                .index()
                .filter(tuple -> hasValidDate(tuple.getT1(), tuple.getT2()))
                .collectList()
                .doOnNext(filteredList -> LOGGER.info("Documents dates check - resulting list {}", filteredList))
                .map(filteredList -> filteredList.size() == userAccreditationRequest.getPayload().getDocuments().size())
                .doOnSuccess(it -> LOGGER.info("Documents dates check - finished with {} for {}", it, userAccreditationRequest));
    }

    private Mono<Boolean> checkDocumentName(UserAccreditationRequest userAccreditationRequest) {
        return Flux.fromIterable(userAccreditationRequest.getPayload().getDocuments())
                .map(document -> {
                    Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);
                    var documentName = document.getName();
                    var hasCorrectFormat = pattern.matcher(documentName).matches();

                    if(!hasCorrectFormat){
                        LOGGER.error("Document dates check - has incorrect format {}", documentName);
                    }
                    return hasCorrectFormat;
                })
                .reduce((resultA, resultB) -> resultA && resultB);


    }

    //This method assumes that the years will always be sent in order (e.g. the first one will be (assuming year = 2021)
    //first - 2020, second 2019
    private Boolean hasValidDate(long index, UserAccreditationRequestPayloadDocument document) {
        var documentName = document.getFileName();
        var documentYear = Integer.parseInt(documentName);
        var hasValidDate = LocalDate.now().minusYears(index+1).getYear() == documentYear;
        if(!hasValidDate)
            LOGGER.error("Documents dates check - document {}.{} with invalid date", index, documentName);
        return hasValidDate;
    }


    private Mono<Boolean> checkNumberOfDocuments(UserAccreditationRequest userAccreditationRequest){
        return Mono.just(userAccreditationRequest)
                .map(it -> it.getPayload().getDocuments().size() >= 2)
                .doOnNext(it -> LOGGER. info("Number of documents validation result request {},  {}", userAccreditationRequest, it));
    }

}
