package io.foodful.dinnerservice.service;

import io.foodful.dinnerservice.domain.Dinner;
import io.foodful.dinnerservice.domain.RSVP;
import io.foodful.dinnerservice.errors.DinnerNotFoundException;
import io.foodful.dinnerservice.errors.UserAlreadyInvitedException;
import io.foodful.dinnerservice.repository.DinnerRepository;
import io.foodful.dinnerservice.service.message.DinnerCreationMessage;
import io.foodful.dinnerservice.service.message.DinnerInviteMessage;
import io.foodful.dinnerservice.service.message.DinnerResult;
import io.foodful.dinnerservice.service.message.DinnerUpdateMessage;
import org.springframework.security.access.AccessDeniedException;
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

    public DinnerResult get(String dinnerId, String principalUserId) {
        Dinner dinner = getByIdOrThrowException(dinnerId);

        if (isCreatorOrAttendeeOfTheDinner(principalUserId, dinner)) {
            return dinnerToDinnerResult(dinner);
        } else {
            throw new DinnerNotFoundException();
        }
    }

    private boolean isCreatorOrAttendeeOfTheDinner(String principalUserId, Dinner dinner) {
        return isCreatorTheSameAsPrincipal(principalUserId, dinner.getCreatedBy()) ||
                dinner.getRsvps().stream().anyMatch(rsvp -> isCreatorTheSameAsPrincipal(principalUserId, rsvp.getUserId()));
    }

    private Dinner getByIdOrThrowException(String dinnerId) {
        return dinnerRepository.findById(dinnerId)
                .orElseThrow(DinnerNotFoundException::new);
    }

    public DinnerResult update(DinnerUpdateMessage message) {
        Dinner toUpdate = getByIdOrThrowException(message.dinnerId);

        if (isCreatorTheSameAsPrincipal(message.userId, toUpdate.getCreatedBy())){
            message.title.ifPresent(toUpdate::setTitle);
            message.location.ifPresent(toUpdate::setLocation);
            message.scheduledTime.ifPresent(toUpdate::setScheduledTime);

            return dinnerToDinnerResult(dinnerRepository.save(toUpdate));

        } else {
            throw new AccessDeniedException("Can not modify this dinner");
        }
    }

    public void delete(String dinnerId, String userId) {
        Dinner dinner = getByIdOrThrowException(dinnerId);

        if (isCreatorTheSameAsPrincipal(userId, dinner.getCreatedBy())){
            dinnerRepository.deleteById(dinnerId);
        } else {
            throw new AccessDeniedException("Can not delete this dinner");
        }
    }

    private boolean isCreatorTheSameAsPrincipal(String userId, String createdBy) {
        return createdBy.equals(userId);
    }


    public DinnerResult invite(DinnerInviteMessage message) {
        Dinner dinner = getByIdOrThrowException(message.dinnerId);

        if (isUserInvitedToDinner(message.userId, dinner)) {
            throw new UserAlreadyInvitedException();
        } else {
            RSVP rsvp = createRsvpFromString(message.userId);
            rsvp.setDinner(dinner);
            dinner.getRsvps().add(rsvp);
            return dinnerToDinnerResult(dinnerRepository.save(dinner));
        }
    }

    private boolean isUserInvitedToDinner(String userId, Dinner dinnerToInviteTo) {
        return dinnerToInviteTo.getRsvps().stream().anyMatch(rsvp -> isCreatorTheSameAsPrincipal(userId, rsvp.getUserId()));
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
