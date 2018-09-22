package io.foodful.dinnerservice.controller;

import io.foodful.dinnerservice.dto.DinnerCreationRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import io.foodful.dinnerservice.service.DinnerService;
import io.foodful.dinnerservice.service.message.DinnerResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.foodful.dinnerservice.util.DinnerConverter.dinnerCreationRequestToMessage;
import static io.foodful.dinnerservice.util.DinnerConverter.dinnerResultToResponse;

@RestController
@RequestMapping("/dinner")
public class DinnerController {

    private DinnerService dinnerService;

    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @PostMapping("")
    public DinnerResponse createDinner(@RequestBody DinnerCreationRequest request) {
        DinnerResult result = dinnerService.create(dinnerCreationRequestToMessage(request));
        return dinnerResultToResponse(result);
    }
}
