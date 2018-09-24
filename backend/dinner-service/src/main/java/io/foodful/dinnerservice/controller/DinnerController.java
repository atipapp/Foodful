package io.foodful.dinnerservice.controller;

import io.foodful.dinnerservice.dto.DinnerCreateRequest;
import io.foodful.dinnerservice.dto.DinnerInviteRequest;
import io.foodful.dinnerservice.dto.DinnerResponse;
import io.foodful.dinnerservice.dto.DinnerUpdateRequest;
import io.foodful.dinnerservice.service.DinnerService;
import io.foodful.dinnerservice.service.message.DinnerResult;
import io.foodful.dto.AuthInfo;
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
        dinnerService.delete(dinnerId);
    }

    @PostMapping("/{dinnerId}/invite")
    public DinnerResponse inviteToDinner(@PathVariable String dinnerId, @RequestBody DinnerInviteRequest request, @RequestAttribute AuthInfo authInfo) {
        DinnerResult result = dinnerService.invite(dinnerInviteRequestToMessage(dinnerId, request));
        return dinnerResultToResponse(result);
    }

    @DeleteMapping("/{dinnerId}/invite")
    public DinnerResponse uninviteFromDinner(@PathVariable String dinnerId, @RequestBody DinnerInviteRequest request, @RequestAttribute AuthInfo authInfo) {
        DinnerResult result = dinnerService.unInvite(dinnerInviteRequestToMessage(dinnerId, request));
        return dinnerResultToResponse(result);
    }

}
