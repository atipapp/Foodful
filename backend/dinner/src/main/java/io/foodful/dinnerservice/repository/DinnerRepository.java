package io.foodful.dinnerservice.repository;

import io.foodful.dinnerservice.domain.Dinner;
import org.springframework.data.repository.CrudRepository;

public interface DinnerRepository extends CrudRepository<Dinner, String> {
}
