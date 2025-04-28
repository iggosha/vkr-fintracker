package ru.golovkov.fintracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovkov.fintracker.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAllByClientId(UUID clientId);

    void deleteByClientId(UUID clientId);
}