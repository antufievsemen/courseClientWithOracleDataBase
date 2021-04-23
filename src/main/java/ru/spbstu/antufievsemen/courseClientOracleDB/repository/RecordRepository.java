package ru.spbstu.antufievsemen.courseClientOracleDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.antufievsemen.courseClientOracleDB.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
    boolean existsRecordBy(Record record);
}
