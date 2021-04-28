package ru.spbstu.antufievsemen.courseClientOracleDB.exception;

public class DeleteNullRecordException extends RuntimeException {
    public DeleteNullRecordException(String message) {
        super(message);
    }
}
