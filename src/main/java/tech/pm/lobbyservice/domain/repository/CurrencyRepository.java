package tech.pm.lobbyservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import tech.pm.lobbyservice.domain.model.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {

  Currency findByTitle(String title);
}
