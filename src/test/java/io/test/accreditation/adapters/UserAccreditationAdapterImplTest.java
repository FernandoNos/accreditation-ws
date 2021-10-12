package io.test.accreditation.adapters;

import io.test.accreditation.controllers.dto.UserAccreditationRequestDTO;
import io.test.accreditation.controllers.dto.UserAccreditationRequestPayloadDTO;
import io.test.accreditation.controllers.dto.UserAccreditationRequestPayloadDocumentDTO;
import io.test.accreditation.enums.AccreditationType;
import io.test.accreditation.exceptions.InvalidNumberOfDocumentsException;
import io.test.accreditation.models.UserAccreditationRequest;
import io.test.accreditation.models.UserAccreditationRequestPayload;
import io.test.accreditation.models.UserAccreditationRequestPayloadDocument;
import io.test.accreditation.models.AccreditationResult;
import io.test.accreditation.services.UserAccreditationServiceImpl;
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

import static org.mockito.Matchers.any;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAccreditationAdapterImplTest {

    @Mock
    private UserAccreditationServiceImpl userAccreditationService;
    @InjectMocks
    private UserAccreditationAdapterImpl userAccreditationAdapter;

    @BeforeAll
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnSuccess_whenValidRequestIsProvided(){
        var request = createValidRequestDTO();

        Mockito.when(userAccreditationService.processUserAccreditation(any(UserAccreditationRequest.class))).thenReturn(Mono.just(
                new AccreditationResult(true, true)
        ));

        var result = this.userAccreditationAdapter.processAccreditationRequest(request).block();
        assert result != null;
        Assertions.assertTrue(result.getAccredited());
        Assertions.assertTrue(result.getSuccess());
    }

    @Test
    public void shoudThrowException_whenUserAccreditationServiceThrowsException(){

        Assertions.assertThrows(InvalidNumberOfDocumentsException.class, () -> {
            var request = createValidRequestDTO();
            Mockito.when(userAccreditationService.processUserAccreditation(any(UserAccreditationRequest.class))).thenThrow(new InvalidNumberOfDocumentsException());
            this.userAccreditationAdapter.processAccreditationRequest(request).block();
        });
    }

    private UserAccreditationRequestDTO createValidRequestDTO() {
        return new UserAccreditationRequestDTO("123", new UserAccreditationRequestPayloadDTO(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocumentDTO("application/pdf",
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocumentDTO("application/jpg",
                                LocalDate.now().minusYears(2).getYear() + ".jpg",
                                "def456"))));
    }

    private UserAccreditationRequest createValidRequest() {
        return new UserAccreditationRequest("123", new UserAccreditationRequestPayload(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocument("application/pdf",
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocument("application/jpg",
                                LocalDate.now().minusYears(2).getYear() + ".jpg",
                                "def456"))));
    }
}
