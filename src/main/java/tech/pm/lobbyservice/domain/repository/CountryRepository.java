package tech.pm.lobbyservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import tech.pm.lobbyservice.domain.model.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
