package ru.spbstu.antufievsemen.courseClientOracleDB.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
}
