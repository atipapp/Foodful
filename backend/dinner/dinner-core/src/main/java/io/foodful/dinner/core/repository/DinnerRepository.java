package io.foodful.dinner.core.repository;

import io.foodful.dinner.core.domain.Dinner;
import org.springframework.data.repository.CrudRepository;

public interface DinnerRepository extends CrudRepository<Dinner, String> {
}
