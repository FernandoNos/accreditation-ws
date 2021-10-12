package io.test.accreditation.services;

import io.test.accreditation.enums.AccreditationType;
import io.test.accreditation.exceptions.InvalidAccreditationTypeException;
import io.test.accreditation.models.*;
import io.test.accreditation.usecases.accreditation.byincome.handler.AccreditationByIncomeRequestHandler;
import io.test.accreditation.usecases.accreditation.byincome.validators.AccreditationByIncomeValidatorUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAccreditationServiceImplTest {

    @Mock
    private AccreditationByIncomeValidatorUseCaseImpl AccreditationByIncomeValidatorUseCase;
    @Mock
    private AccreditationByIncomeRequestHandler accreditationByIncomeRequestHandler;

    @InjectMocks
    private UserAccreditationServiceImpl userAccreditationServiceImpl;

    @BeforeAll
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldFinishWithSuccess_whenValidByIncomeRequestIsReceived(){
        var request = createRequest();

        Mockito.when(AccreditationByIncomeValidatorUseCase.validate(request)).thenReturn(
                Mono.just(true)
        );

        Mockito.when(accreditationByIncomeRequestHandler.handleAccreditationRequest(request)).thenReturn(
                Mono.just(new AccreditationResult(true, true))
        );

        var result = this.userAccreditationServiceImpl.processUserAccreditation(request).block();
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.isAccredited());
    }

    @Test
    public void shouldThrowInvalidAccreditationRequestException_whenInvalidRequestIsProvided(){
        Assertions.assertThrows(InvalidAccreditationTypeException.class, () -> {

            var request = createInvalidRequest_noAccreditationType();

            Mockito.when(AccreditationByIncomeValidatorUseCase.validate(request)).thenReturn(
                    Mono.just(false)
            );

            Mockito.when(accreditationByIncomeRequestHandler.handleAccreditationRequest(request)).thenReturn(
                    Mono.just(new AccreditationResult(true, true))
            );

            this.userAccreditationServiceImpl.processUserAccreditation(request).block();
        });
    }

    private UserAccreditationRequest createInvalidRequest_noAccreditationType() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                null,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                String.valueOf(LocalDate.now().minusYears(1).getYear()),
                                "abc123"),
                        new UserAccreditationRequestPayloadDocument("application/jpg",
                                String.valueOf(LocalDate.now().minusYears(2).getYear()),
                                "def456"))));
    }

    private UserAccreditationRequest createRequest() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                String.valueOf(LocalDate.now().minusYears(1).getYear()),
                                "abc123"),
                        new UserAccreditationRequestPayloadDocument("application/jpg",
                                String.valueOf(LocalDate.now().minusYears(2).getYear()),
                                "def456"))));
    }
}
