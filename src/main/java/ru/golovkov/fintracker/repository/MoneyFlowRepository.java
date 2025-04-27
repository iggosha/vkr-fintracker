package ru.golovkov.fintracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovkov.fintracker.model.MoneyFlow;

public interface MoneyFlowRepository extends JpaRepository<MoneyFlow, String> {

}