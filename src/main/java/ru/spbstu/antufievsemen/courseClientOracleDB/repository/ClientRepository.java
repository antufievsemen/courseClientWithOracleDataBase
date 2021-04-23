package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existClientBy(Client client);
}
