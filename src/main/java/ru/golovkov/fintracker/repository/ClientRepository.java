package ru.golovkov.fintracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.golovkov.fintracker.model.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

}