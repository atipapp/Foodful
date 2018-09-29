package io.foodful.dinnerservice.service;

import io.foodful.dinnerservice.domain.Dinner;
import io.foodful.dinnerservice.errors.DinnerNotFoundException;
import io.foodful.dinnerservice.repository.DinnerRepository;
import org.springframework.stereotype.Service;

@Service
public class DinnerSecurityService {

    private DinnerRepository dinnerRepository;

    public DinnerSecurityService(DinnerRepository dinnerRepository) {
        this.dinnerRepository = dinnerRepository;
    }

    public boolean isAttendeeOfTheDinner(String dinnerId, String userId){
        Dinner dinner = dinnerRepository.findById(dinnerId)
                .orElseThrow(DinnerNotFoundException::new);

        return userId.equals(dinner.getCreatedBy()) ||
                dinner.getRsvps().stream()
                        .anyMatch(rsvp -> rsvp.getUserId().equals(userId));
    }

    public boolean isCreatorOfTheDinner(String dinnerId, String userId){
        Dinner dinner = dinnerRepository.findById(dinnerId)
                .orElseThrow(DinnerNotFoundException::new);

        return userId.equals(dinner.getCreatedBy());
    }

}
