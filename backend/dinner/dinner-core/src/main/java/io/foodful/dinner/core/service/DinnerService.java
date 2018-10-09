package io.foodful.dinner.core.service;

import io.foodful.dinner.core.domain.Dinner;
import io.foodful.dinner.core.domain.RSVP;
import io.foodful.dinner.core.errors.DinnerNotFoundException;
import io.foodful.dinner.core.repository.DinnerRepository;
import io.foodful.dinner.core.service.message.DinnerCreationMessage;
import io.foodful.dinner.core.service.message.DinnerInviteMessage;
import io.foodful.dinner.core.service.message.DinnerResult;
import io.foodful.dinner.core.service.message.DinnerUpdateMessage;
import io.foodful.dinner.core.errors.UserAlreadyInvitedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DinnerService {

    private DinnerRepository dinnerRepository;

    public DinnerService(DinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    public DinnerResult create(DinnerCreationMessage message) {
        Dinner dinner = Dinner.builder()
                .title(message.title)
                .location(message.location)
                .comments(new ArrayList<>())
                .rsvps(message.guests.stream().map(this::createRsvpFromString).collect(Collectors.toList()))
                .scheduledTime(message.scheduledTime)
                .createdBy(message.userId)
                .build();

        dinner.getRsvps().forEach(rsvp -> rsvp.setDinner(dinner));
        return dinnerToDinnerResult(dinnerRepository.save(dinner));
    }

    @PreAuthorize("@dinnerSecurityService.isAttendeeOfTheDinner(#dinnerId, #principalUserId)")
    public DinnerResult get(String dinnerId, String principalUserId) {
        Dinner dinner = getByIdOrThrowException(dinnerId);

        return dinnerToDinnerResult(dinner);
    }

    private Dinner getByIdOrThrowException(String dinnerId) {
        return dinnerRepository.findById(dinnerId)
                .orElseThrow(DinnerNotFoundException::new);
    }

    @PreAuthorize("@dinnerSecurityService.isCreatorOfTheDinner(#message.dinnerId, #message.principalUserId)")
    public DinnerResult update(DinnerUpdateMessage message) {
        Dinner toUpdate = getByIdOrThrowException(message.dinnerId);

        message.title.ifPresent(toUpdate::setTitle);
        message.location.ifPresent(toUpdate::setLocation);
        message.scheduledTime.ifPresent(toUpdate::setScheduledTime);

        return dinnerToDinnerResult(dinnerRepository.save(toUpdate));
    }

    @PreAuthorize("@dinnerSecurityService.isCreatorOfTheDinner(#dinnerId, #principalUserId)")
    public void delete(String dinnerId, String principalUserId) {
        dinnerRepository.deleteById(dinnerId);
    }

    @PreAuthorize("@dinnerSecurityService.isAttendeeOfTheDinner(#message.dinnerId, #message.principalUserId)")
    public DinnerResult invite(DinnerInviteMessage message) {
        Dinner dinner = getByIdOrThrowException(message.dinnerId);

        if (isUserInvitedToDinner(message.invitedUserId, dinner)) {
            throw new UserAlreadyInvitedException();
        } else {
            RSVP rsvp = createRsvpFromString(message.invitedUserId);
            rsvp.setDinner(dinner);
            dinner.getRsvps().add(rsvp);
            return dinnerToDinnerResult(dinnerRepository.save(dinner));
        }
    }

    private boolean isUserInvitedToDinner(String userId, Dinner dinnerToInviteTo) {
        return dinnerToInviteTo.getRsvps().stream().anyMatch(rsvp -> userId.equals(rsvp.getUserId()));
    }

    private DinnerResult dinnerToDinnerResult(Dinner dinner) {
        return DinnerResult.builder()
                .id(dinner.getId())
                .title(dinner.getTitle())
                .guests(dinner.getRsvps().stream().collect(Collectors.toMap(
                        RSVP::getUserId,
                        rsvp -> rsvp.getStatus().name())))
                .location(dinner.getLocation())
                .scheduledTime(dinner.getScheduledTime().toString())
                .createdBy(dinner.getCreatedBy())
                .build();
    }

    private RSVP createRsvpFromString(String userId) {
        return RSVP.builder()
                .userId(userId)
                .status(RSVP.Status.PENDING)
                .build();
    }

}
