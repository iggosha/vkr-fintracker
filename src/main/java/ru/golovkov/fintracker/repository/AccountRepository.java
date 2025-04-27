package ru.golovkov.fintracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovkov.fintracker.model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}