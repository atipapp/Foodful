package io.foodful.dinner.core.controller;

import io.foodful.commons.core.dto.AuthInfo;
import io.foodful.dinner.api.dto.DinnerCreateRequest;
import io.foodful.dinner.api.dto.DinnerInviteRequest;
import io.foodful.dinner.api.dto.DinnerResponse;
import io.foodful.dinner.api.dto.DinnerUpdateRequest;
import io.foodful.dinner.core.service.message.DinnerResult;
import io.foodful.dinner.core.service.DinnerService;
import org.springframework.web.bind.annotation.*;

import static io.foodful.dinner.core.util.DinnerConverter.*;

@RestController
@RequestMapping("/dinner")
public class DinnerController {

    private DinnerService dinnerService;

    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @PostMapping("")
    public DinnerResponse createDinner(@RequestBody DinnerCreateRequest request, @RequestAttribute AuthInfo authInfo) {
        DinnerResult result = dinnerService.create(dinnerCreateRequestToMessage(request, authInfo.userId));
        return dinnerResultToResponse(result);
    }

    @GetMapping("/{dinnerId}")
    public DinnerResponse getDinner(@PathVariable String dinnerId, @RequestAttribute AuthInfo authInfo) {
        DinnerResult result = dinnerService.get(dinnerId, authInfo.userId);
        return dinnerResultToResponse(result);
    }

    @PutMapping("/{dinnerId}")
    public DinnerResponse updateDinner(@PathVariable String dinnerId, @RequestBody DinnerUpdateRequest request, @RequestAttribute AuthInfo authInfo) {
        DinnerResult result = dinnerService.update(dinnerUpdateRequestToMessage(dinnerId, request, authInfo.userId));
        return dinnerResultToResponse(result);
    }

    @DeleteMapping("/{dinnerId}")
    public void deleteDinner(@PathVariable String dinnerId, @RequestAttribute AuthInfo authInfo) {
        dinnerService.delete(dinnerId, authInfo.userId);
    }

    @PostMapping("/{dinnerId}/invite")
    public DinnerResponse inviteToDinner(@PathVariable String dinnerId, @RequestBody DinnerInviteRequest request, @RequestAttribute AuthInfo authInfo) {
        DinnerResult result = dinnerService.invite(dinnerInviteRequestToMessage(dinnerId, request, authInfo.userId));
        return dinnerResultToResponse(result);
    }

}
