package io.test.accreditation.controllers;

import io.test.accreditation.adapters.UserAccreditationAdapter;
import io.test.accreditation.controllers.dto.UserAccreditationRequestDTO;
import io.test.accreditation.controllers.dto.UserAccreditationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserAccreditationController {

    @Autowired
    private UserAccreditationAdapter userAccreditationAdapter;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @PostMapping("/accreditation")
    public Mono<UserAccreditationResponseDTO> postAccreditation(@Valid @RequestBody UserAccreditationRequestDTO accreditationRequestDTO){
        return Mono.just(accreditationRequestDTO)
                .doOnNext(request -> LOGGER.info("Accreditation request received {}", request.toString()))
                .flatMap(it -> this.userAccreditationAdapter.processAccreditationRequest(accreditationRequestDTO))
                .doOnSuccess(result -> LOGGER.info("Accreditation result {}", result))
                .doOnError(error -> LOGGER.error("Accreditation finished with error - {} - request {}", error.getMessage(), accreditationRequestDTO));
    }

}
