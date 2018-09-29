package io.foodful.dinner.core.service.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DinnerInviteMessage {

    public String dinnerId;
    public String invitedUserId;
    public String principalUserId;

}
