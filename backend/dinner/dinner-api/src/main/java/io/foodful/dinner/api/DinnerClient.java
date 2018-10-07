package io.foodful.dinner.api;

import com.sun.tools.internal.ws.wscompile.AuthInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dinner-service-v1", url = "${foodful.clients.prefix}dinner-service-v1${foodful.clients.postfix}")
public interface DinnerClient {

    @PostMapping("/dinner")
    DinnerResponse createDinner(@RequestBody DinnerCreateRequest request, @RequestAttribute AuthInfo authInfo);

    @GetMapping("/dinner/{dinnerId}")
    DinnerResponse getDinner(@PathVariable String dinnerId, @RequestAttribute AuthInfo authInfo);

    @PutMapping("/dinner/{dinnerId}")
    DinnerResponse updateDinner(@PathVariable String dinnerId, @RequestBody DinnerUpdateRequest request, @RequestAttribute AuthInfo authInfo);

    @DeleteMapping("/dinner/{dinnerId}")
    void deleteDinner(@PathVariable String dinnerId, @RequestAttribute AuthInfo authInfo);

    @PostMapping("/dinner/{dinnerId}/invite")
    DinnerResponse inviteToDinner(@PathVariable String dinnerId, @RequestBody DinnerInviteRequest request, @RequestAttribute AuthInfo authInfo);

}
