package io.test.accreditation.usecases.accreditation.byincome.validators;

import io.test.accreditation.enums.AccreditationType;
import io.test.accreditation.exceptions.InvalidNumberOfDocumentsException;
import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.UserAccreditationRequestPayload;
import io.test.accreditation.models.UserAccreditationRequestPayloadDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(MockitoJUnitRunner.class)
public class ByIncomeAccreditationValidatorUseCaseTest {

    @Spy
    private AccreditationByIncomeValidatorUseCaseImpl byIncomeAccreditationValidatorUseCase = new AccreditationByIncomeValidatorUseCaseImpl();

    @BeforeAll
    public void setUp() {
        ReflectionTestUtils.setField(byIncomeAccreditationValidatorUseCase, "FILE_NAME_PATTERN", "^[0-9]+\\.(pdf|jpg)$");
    }

    @Test
    public void shouldReturnSuccess_whenValidUserAccreditationRequestIsReceived(){
        var request = createValidRequest();

        var result = this.byIncomeAccreditationValidatorUseCase.validate(request).block();
        assert result != null;
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnFalse_whenLessThan2DocumentsAreProvided(){
            var request = createInvalidRequest_OnlyOneDocument();
            var result = this.byIncomeAccreditationValidatorUseCase.validate(request).block();
            Assertions.assertFalse(result);
    }

    @Test
    public void shouldReturnFalse_whenDocumentsWithInvalidNamesAreProvided(){
            var request = createInvalidRequest_TwoDocumentButWithInvalidNames();
            var result = this.byIncomeAccreditationValidatorUseCase.validate(request).block();
            Assertions.assertFalse(result);
    }

    @Test
    public void shouldReturnFalse_whenDocumentsWithInvalidDatesAreProvided(){
            var request = createInvalidRequest_TwoDocumentButWithInvalidDates();
            var result = this.byIncomeAccreditationValidatorUseCase.validate(request).block();
            Assertions.assertFalse(result);
    }

    private UserAccreditationRequest createInvalidRequest_OnlyOneDocument() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                String.valueOf(LocalDate.now().minusYears(1).getYear()),
                                "abc123"))));
    }

    private UserAccreditationRequest createInvalidRequest_TwoDocumentButWithInvalidDates() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                LocalDate.now().minusYears(2).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocument("application/jpg",
                                LocalDate.now().minusYears(3).getYear() + ".jpg",
                                "def456"))));
    }

    private UserAccreditationRequest createInvalidRequest_TwoDocumentButWithInvalidNames() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                String.valueOf(LocalDate.now().minusYears(1).getYear()),
                                "abc123"),
                        new UserAccreditationRequestPayloadDocument("application/jpg",
                                String.valueOf(LocalDate.now().minusYears(2).getYear()),
                                "def456"))));
    }

    private UserAccreditationRequest createValidRequest() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                String.valueOf(LocalDate.now().minusYears(1).getYear()+".pdf"),
                                "abc123"),
                        new UserAccreditationRequestPayloadDocument("application/jpg",
                                String.valueOf(LocalDate.now().minusYears(2).getYear()+".jpg"),
                                "def456"))));
    }

}
