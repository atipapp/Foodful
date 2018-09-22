package io.foodful.dinnerservice.controller;

import io.foodful.dinnerservice.dto.DinnerCreateRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import io.foodful.dinnerservice.dto.DinnerUpdateRequest;
import io.foodful.dinnerservice.service.DinnerService;
import io.foodful.dinnerservice.service.message.DinnerResult;
import org.springframework.web.bind.annotation.*;

import static io.foodful.dinnerservice.util.DinnerConverter.*;

@RestController
@RequestMapping("/dinner")
public class DinnerController {

    private DinnerService dinnerService;

    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @PostMapping("")
    public DinnerResponse createDinner(@RequestBody DinnerCreateRequest request) {
        DinnerResult result = dinnerService.create(dinnerCreateRequestToMessage(request));
        return dinnerResultToResponse(result);
    }

    @GetMapping("/{dinnerId}")
    public DinnerResponse getDinner(@PathVariable String dinnerId) {
        DinnerResult result = dinnerService.get(dinnerId);
        return dinnerResultToResponse(result);
    }

    @PutMapping("/{dinnerId}")
    public DinnerResponse updateDinner(@PathVariable String dinnerId, @RequestBody DinnerUpdateRequest request) {
        DinnerResult result = dinnerService.update(dinnerUpdateRequestToMessage(dinnerId, request));
        return dinnerResultToResponse(result);
    }

    @DeleteMapping("/{dinnerId}")
    public void deleteDinner(@PathVariable String dinnerId) {
        dinnerService.delete(dinnerId);
    }

}
