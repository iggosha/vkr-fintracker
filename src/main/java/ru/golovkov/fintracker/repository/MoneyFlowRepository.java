package ru.golovkov.fintracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovkov.fintracker.model.MoneyFlow;

import java.util.UUID;

public interface MoneyFlowRepository extends JpaRepository<MoneyFlow, String> {

    Page<MoneyFlow> findAllByAccountId(String accountId, Pageable pageable);

    Page<MoneyFlow> findAllByAccountClientId(UUID clientId, Pageable pageable);

}