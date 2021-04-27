package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "Select COUNT(*) From journal \n" +
            "    Where client_id = ?1 AND (date_return) IS NULL",
    nativeQuery = true)
    int countByDateReturnIsNullAndClientEquals(long id);
}
