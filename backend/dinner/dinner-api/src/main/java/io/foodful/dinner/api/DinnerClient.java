package io.foodful.dinner.api;

import io.foodful.dinner.api.dto.DinnerCreateRequest;
import io.foodful.dinner.api.dto.DinnerInviteRequest;
import io.foodful.dinner.api.dto.DinnerResponse;
import io.foodful.dinner.api.dto.DinnerUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dinner-service", url = "${foodful.clients.prefix}dinner-service${foodful.clients.postfix}")
public interface DinnerClient {

    @PostMapping("/dinner")
    DinnerResponse createDinner(@RequestBody DinnerCreateRequest request);

    @GetMapping("/dinner/{dinnerId}")
    DinnerResponse getDinner(@PathVariable("dinnerId") String dinnerId);

    @PutMapping("/dinner/{dinnerId}")
    DinnerResponse updateDinner(@PathVariable("dinnerId") String dinnerId, @RequestBody DinnerUpdateRequest request);

    @DeleteMapping("/dinner/{dinnerId}")
    void deleteDinner(@PathVariable("dinnerId") String dinnerId);

    @PostMapping("/dinner/{dinnerId}/invite")
    DinnerResponse inviteToDinner(@PathVariable("dinnerId") String dinnerId, @RequestBody DinnerInviteRequest request);

}
