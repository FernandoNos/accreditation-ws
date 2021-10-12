package io.test.accreditation.controllers;

import io.test.accreditation.adapters.UserAccreditationAdapterImpl;
import io.test.accreditation.controllers.dto.UserAccreditationRequestDTO;
import io.test.accreditation.controllers.dto.UserAccreditationRequestPayloadDTO;
import io.test.accreditation.controllers.dto.UserAccreditationRequestPayloadDocumentDTO;
import io.test.accreditation.controllers.dto.UserAccreditationResponseDTO;
import io.test.accreditation.enums.AccreditationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(UserAccreditationController.class)
public class UserAccreditationControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UserAccreditationAdapterImpl userAccreditationAdapter;

    @Test
    public void shouldReturnSuccess_whenValidRequestIsProvided(){
        var request = createValidRequestDTO();

        Mockito.when(this.userAccreditationAdapter.processAccreditationRequest(any())).thenReturn(Mono.just(
                new UserAccreditationResponseDTO(true,true)
        ));

        var response = this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserAccreditationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserAccreditationResponseDTO.class);
        var body = response.returnResult().getResponseBody();
        assert body != null;
        Assertions.assertTrue(body.getSuccess());
        Assertions.assertTrue(body.getAccredited());
    }

    @Test
    public void shouldReturnBadRequest_whenBodyIsEmpty(){
        var request = createInvalidDTO_noPayload();

        Mockito.when(this.userAccreditationAdapter.processAccreditationRequest(any())).thenReturn(Mono.just(
                new UserAccreditationResponseDTO(true,true)
        ));

        this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)

                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void shouldReturnBadRequest_whenBodyHasNoPayload(){
        var request = createInvalidDTO_noPayload();

        this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserAccreditationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void shouldReturnBadRequest_whenBodyHasNoAccreditationType(){
        var request = createInvalidDTO_noAccreditationType();

        this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserAccreditationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
    @Test
    public void shouldReturnBadRequest_whenBodyHasNoMimeType(){
        var request = createInvalidDTO_noMimeType();

        this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserAccreditationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
    @Test
    public void shouldReturnBadRequest_whenBodyHasNoName(){
        var request = createInvalidDTO_noName();

        this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserAccreditationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void shouldReturnBadRequest_whenBodyHasNoContent(){
        var request = createInvalidDTO_noContent();

        this.webTestClient.post()
                .uri("/user/accreditation")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UserAccreditationRequestDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
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

    private UserAccreditationRequestDTO createInvalidDTO_noName() {
        return new UserAccreditationRequestDTO("123", new UserAccreditationRequestPayloadDTO(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocumentDTO("application/pdf",
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocumentDTO("application/jpg",
                                null,
                                "def456"))));
    }

    private UserAccreditationRequestDTO createInvalidDTO_noContent() {
        return new UserAccreditationRequestDTO("123", new UserAccreditationRequestPayloadDTO(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocumentDTO("application/pdf",
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocumentDTO("application/jpg",
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                null))));
    }

    private UserAccreditationRequestDTO createInvalidDTO_noMimeType() {
        return new UserAccreditationRequestDTO("123", new UserAccreditationRequestPayloadDTO(
                AccreditationType.BY_INCOME,
                List.of(new UserAccreditationRequestPayloadDocumentDTO(null,
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocumentDTO("application/jpg",
                                LocalDate.now().minusYears(2).getYear() + ".jpg",
                                "def456"))));
    }

    private UserAccreditationRequestDTO createInvalidDTO_noAccreditationType() {
        return new UserAccreditationRequestDTO("123", new UserAccreditationRequestPayloadDTO(
                null,
                List.of(new UserAccreditationRequestPayloadDocumentDTO("application/pdf",
                                LocalDate.now().minusYears(1).getYear() + ".pdf",
                                "abc123"),
                        new UserAccreditationRequestPayloadDocumentDTO("application/jpg",
                                LocalDate.now().minusYears(2).getYear() + ".jpg",
                                "def456"))));
    }

    private UserAccreditationRequestDTO createInvalidDTO_noPayload() {
        return new UserAccreditationRequestDTO("123", null);
    }

}
